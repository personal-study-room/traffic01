package com.onion.common.domain;

import lombok.Data;

@Data
public class ApiErrorResponse {

    private String error;
    private String message;

    private ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public static ApiErrorResponse of(String error, String message) {
        return new ApiErrorResponse(error, message);
    }
}
