package com.onion.common.exception;

import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

@Getter
public class CustomAccessDeniedException extends AccessDeniedException {

    private String message;
    private String error;


    public CustomAccessDeniedException(String msg) {
        super(msg);
    }

    public CustomAccessDeniedException(String msg, String error) {
        super(msg);
        this.message = msg;
        this.error = error;
    }
}
