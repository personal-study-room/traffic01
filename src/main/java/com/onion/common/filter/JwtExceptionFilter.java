package com.onion.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onion.common.domain.ApiErrorResponse;
import com.onion.common.exception.CustomAccessDeniedException;
import com.onion.common.exception.CustomAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomAuthenticationException customAuthenticationException) {
            String error = customAuthenticationException.getError();
            String message = customAuthenticationException.getMessage();

            setResponse(error, message, HttpStatus.FORBIDDEN, response);
        } catch (CustomAccessDeniedException customAccessDeniedException) {
            String error = customAccessDeniedException.getError();
            String message = customAccessDeniedException.getMessage();

            setResponse(error, message, HttpStatus.UNAUTHORIZED, response);
        } catch (Exception e) {
            setResponse(e.getMessage(), "unhandled exception", HttpStatus.UNAUTHORIZED, response);
        }
    }

    private void setResponse(String error, String message, HttpStatus status, HttpServletResponse response)
            throws IOException {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(error, message, status.toString());

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String apiErrorResponseString = objectMapper.writeValueAsString(apiErrorResponse);
        response.getWriter().write(apiErrorResponseString);
    }
}
