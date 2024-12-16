package org.project.beecommerceproject.filter;

import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.project.beecommerceproject.configs.CustomerDetail;
import org.project.beecommerceproject.services.JwtService;
import org.project.beecommerceproject.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserServiceImp userServiceImp;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    private final List<String> REQ_PUBLIC = List.of(
            "/public/**",
            "/auth/**"
    );

    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(header);
        if (authHeader != null && authHeader.startsWith(prefix)) {
            return authHeader.substring(7);
        }
        return null;
    }

    public boolean checkPublic(String uri) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return REQ_PUBLIC.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (checkPublic(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String bearerToken = getToken(request);
            if (Strings.hasText(bearerToken) && jwtService.isTokenValid(bearerToken)) {
                String email = jwtService.extractEmai(bearerToken);
                CustomerDetail customerDetail = (CustomerDetail) userServiceImp.loadUserByUsername(email);

                if (customerDetail != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    customerDetail,
                                    null,
                                    customerDetail.getAuthorities()
                            );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.error("Authentication filter error: {}", e.getMessage(), e);
        }
        filterChain.doFilter(request, response);
    }

}
