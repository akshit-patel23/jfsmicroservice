package com.itransform.auth_service.dto;

import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, String> fieldErrors;  // key=field name, value=error message

    public ErrorResponse(String message, Map<String, String> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(String message) {
        this.message = message;
    }

    // getters setters

    public String getMessage() {
        return message;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
