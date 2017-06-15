package com.security.rest.api.handler.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class SecurityApiDefaultErrorMessage {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    
    public SecurityApiDefaultErrorMessage () {
    }

    public SecurityApiDefaultErrorMessage (HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public SecurityApiDefaultErrorMessage (HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
    
}
