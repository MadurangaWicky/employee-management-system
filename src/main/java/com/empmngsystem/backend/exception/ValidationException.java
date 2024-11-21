package com.empmngsystem.backend.exception;

/* For the complex dto validation logics */
public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
