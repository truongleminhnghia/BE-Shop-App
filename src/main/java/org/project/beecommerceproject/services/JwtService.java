package org.project.beecommerceproject.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (extractEmail(token) != null && !isTokenExpired(token)) {
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
