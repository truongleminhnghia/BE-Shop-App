package org.project.beecommerceproject.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.project.beecommerceproject.configs.CustomerUserDetail;
import org.project.beecommerceproject.services.JwtService;
import org.project.beecommerceproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    private final List<String> REQ_PUBLIC = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/public/**"
//            "/auths/**"
    );

    public String getToken(HttpServletRequest request) {
        String str = request.getHeader("Authorization");
        if (str.startsWith("Bearer ") && StringUtils.hasText(str)) {
            return str.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (isAuthenticated(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String bearerToken = getToken(request);
            if (Strings.hasText(bearerToken) && jwtService.isTokenValid(bearerToken)) {
                String email = jwtService.extractEmail(bearerToken);

                CustomerUserDetail customerUserDetail = (CustomerUserDetail) userService.loadUserByUsername(email);
                if (customerUserDetail != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(customerUserDetail, null, customerUserDetail.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            log.error("Token has expired: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Fail on set user authentication: {}", e.toString());
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAuthenticated(String uri) {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return REQ_PUBLIC.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri));
    }
}
