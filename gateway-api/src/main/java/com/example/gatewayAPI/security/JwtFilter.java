package com.example.gatewayAPI.security;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        log.info("JwtFilter: Processing request to path: {}", path);

        if (path.equalsIgnoreCase("/user-service/auth/login") || path.equalsIgnoreCase("/user-service/auth/registration")) {
            log.info("JwtFilter: Skipping JWT validation for public path: {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        try {
            HeaderMapRequestWrapper wrapperRequest = new HeaderMapRequestWrapper(request);
            wrapperRequest.removeHeader("X-User-Email");
            wrapperRequest.removeHeader("X-User-Role");

            String authorization = request.getHeader("Authorization");

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header.");
                return;
            }

            String token = authorization.substring(7);

            if (!jwtService.validateToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
                return;
            }

            String email = jwtService.extractSubject(token);
            String role = jwtService.extractRole(token);

            wrapperRequest.addHeader("X-User-Email", email);
            wrapperRequest.addHeader("X-User-Role", role);

            log.info("JWT Token validated for: {}", email);

            filterChain.doFilter(wrapperRequest, response);
        } catch (Exception e) {
            log.error("Error validating JWT {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Token validation failed.");
        }
    }
}
