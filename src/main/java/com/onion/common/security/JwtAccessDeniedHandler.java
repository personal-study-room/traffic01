package com.onion.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onion.common.domain.ApiErrorResponse;
import com.onion.common.exception.CustomAccessDeniedException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiErrorResponse apiErrorResponse;

        if (accessDeniedException instanceof CustomAccessDeniedException exception) {
            apiErrorResponse = ApiErrorResponse.of(exception.getError(), exception.getMessage());

        } else {
            apiErrorResponse = ApiErrorResponse.of(accessDeniedException.getMessage(), "unHandled...");

        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String apiErrorResponseString = objectMapper.writeValueAsString(apiErrorResponse);
        response.getWriter().write(apiErrorResponseString);
        accessDeniedException.printStackTrace();
    }
}
