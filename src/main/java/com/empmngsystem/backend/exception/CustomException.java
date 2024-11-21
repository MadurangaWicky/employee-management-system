package com.empmngsystem.backend.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final long code;
    private final String message;

    public CustomException(long code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
