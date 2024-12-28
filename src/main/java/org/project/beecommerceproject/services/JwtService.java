package org.project.beecommerceproject.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.dtos.requests.TokenRequest;
import org.project.beecommerceproject.entities.Token;
import org.project.beecommerceproject.enums.EnumTokenType;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET;
    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);
    private static final long LIMIT_REFRESH_TIME = TimeUnit.MINUTES.toMillis(5);
    private static final long TIME_TOKEN_REFESH = TimeUnit.MINUTES.toMillis(10080);
    private static final Date DATE_CREATE_TOKEN = new Date(System.currentTimeMillis());
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    public String generateToken(CustomerUserDetail customerDetail) {
        return Jwts.builder()
                .subject(customerDetail.getUsername())
                .id(UUID.randomUUID().toString())
                .claim("iss", "http://localhost:8080/jwt")
                .claim("userId", customerDetail.getUserID())
                .claim("email", customerDetail.getEmail())
                .claim("role", customerDetail.getAuthorities())
                .issuedAt(DATE_CREATE_TOKEN)
                .expiration(new Date(DATE_CREATE_TOKEN.getTime() + VALIDITY))
                .signWith(generateKey())
                .compact();
    }

    public String refreshToken(TokenRequest request) {
        boolean result = isTokenValid(request.getToken());
        if (result) {
            Date exp = getExpiration(request.getToken());
            String email = extractEmail(request.getToken());
            if (email != null) {
                CustomerUserDetail customer = (CustomerUserDetail) userService.loadUserByUsername(email);
                long remainingTime = exp.getTime() - System.currentTimeMillis();
                if (remainingTime >= LIMIT_REFRESH_TIME) {
                    Token token = tokenService.save(request);
                    if (request.getType().equals(EnumTokenType.ACCESS_TOKEN)) {
                        return Jwts.builder()
                                .subject(customer.getUsername())
                                .id(UUID.randomUUID().toString())
                                .claim("iss", "http://localhost:8080/jwt")
                                .claim("userId", customer.getUserID())
                                .claim("email", customer.getEmail())
                                .claim("role", customer.getAuthorities())
                                .issuedAt(DATE_CREATE_TOKEN)
                                .expiration(new Date(DATE_CREATE_TOKEN.getTime() + TIME_TOKEN_REFESH))
                                .signWith(generateKey())
                                .compact();
                    }
                }
            } else {
                throw new AppException(ErrorCode.EMAIL_NOT_NULL);
            }
        }
        throw new AppException(ErrorCode.TOKEN_DO_NOT_REFRESH);
    }

    private SecretKey generateKey() {
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(decodeKey, "HmacSHA256");
    }

    public String extractEmail(String token) {
        return getClaims(token, Claims::getSubject);
    }
    public String extractJwtId(String token) {
        return getClaims(token, Claims::getId);
    }

    public Date getExpiration(String token) {
        return getClaims(token, Claims::getExpiration);
    }

    private <T> T getClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(generateKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }

    public boolean isTokenValid(String token) {
        if (extractEmail(token) != null && !isTokenExpired(token) && !tokenService.exitById(extractJwtId(token))) {
            return true;
        } else {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaims(token, Claims::getExpiration);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

}
