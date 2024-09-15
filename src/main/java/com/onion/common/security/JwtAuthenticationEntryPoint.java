package com.onion.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onion.common.domain.ApiErrorResponse;
import com.onion.common.exception.CustomAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        ApiErrorResponse apiErrorResponse;

        if (authException instanceof CustomAuthenticationException exception) {
            apiErrorResponse = ApiErrorResponse.of(exception.getError(), exception.getMessage());

        } else {
            apiErrorResponse = ApiErrorResponse.of(authException.getMessage(), "unHandled...");

        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String apiErrorResponseString = objectMapper.writeValueAsString(apiErrorResponse);
        response.getWriter().write(apiErrorResponseString);
        authException.printStackTrace();
    }
}
