
package com.ahmed.move.security.jwt;

import com.ahmed.move.dto.GeneralResponseDto;
import com.ahmed.move.security.enummration.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Set<String> publicRequests = Set.of(
            "swagger",
            "v3/api-docs",
            "refresh-token",
            "auth",
            "swagger-ui",
            "api-docs",
            "sign-up"
    );

    private static final Set<String> adminRequests = Set.of(
            "admin"
    );

    private static final Set<String> userRequests = Set.of(
            "user"
    );

    private final JwtTokenProvider jwtTokenUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {
        try {
            String uri = request.getRequestURI();
            if (!isPublicRequest(uri)) {
                String token = extractToken(request.getHeader("Authorization"));
                if (token == null || !jwtTokenUtil.validateToken(token)) {
                    throwUnAuthorizedError(response);
                    return;
                } else {
                    Role role  = Role.valueOf(jwtTokenUtil.getRoleFromToken(token));
                    if (role == Role.ADMIN && !isAdminRequest(uri)) {
                        throwUnAuthorizedError(response);
                        return;
                    } else if (role == Role.USER && !isUserRequest(uri)) {
                        throwUnAuthorizedError(response);
                        return;
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throwInternalServerError(response,e.getMessage());
        }
    }

    private boolean isPublicRequest(String uri) {

        return publicRequests.stream()
                .anyMatch(uri::contains);
    }

    private boolean isAdminRequest (String uri) {

        return adminRequests.stream()
                .anyMatch(uri::contains);
    }


    private boolean isUserRequest (String uri) {

        return userRequests.stream()
                .anyMatch(uri::contains);
    }

    private String extractToken(String header) {
        String BEARER = "Bearer ";
        if (header != null && header.startsWith(BEARER)) {
            return header.substring(7);
        }
        return null;
    }


    private void throwUnAuthorizedError(HttpServletResponse response) {
        String unauthorizedMessage = "Access Denied: Unauthorized Access Attempt!";
        try {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            GeneralResponseDto payload = GeneralResponseDto.builder()
                    .error(Map.of("errorMessage", unauthorizedMessage))
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), payload);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, unauthorizedMessage);
        }
    }

    private void throwInternalServerError(HttpServletResponse response , String message) {
        try {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            GeneralResponseDto payload = GeneralResponseDto.builder()
                    .data(Map.of("errorMessage", message))
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), payload);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
        }
    }
}
