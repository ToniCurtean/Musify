package com.toni.musify.domain.exceptions;

public class BusinessValidationException extends RuntimeException{
    public BusinessValidationException(String message){
        super(message);
    }
}
