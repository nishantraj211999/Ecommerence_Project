package com.ecommerce.project.exceptions;

public class APIException extends RuntimeException{
    private static final long serialversionID=1L;

    public APIException() {}

    public APIException(String message) {
        super(message);
    }
}
