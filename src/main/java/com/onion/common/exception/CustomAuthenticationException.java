package com.onion.common.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class CustomAuthenticationException extends AuthenticationException {

    private String message;
    private String error;


    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public CustomAuthenticationException(String message, String error) {
        super(message);
        this.message = message;
        this.error = error;
    }
}
