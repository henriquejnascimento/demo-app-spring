package com.henriquenascimento.demo.exceptions;

public class UploadFileValidationException extends RuntimeException {

    public UploadFileValidationException(String message) {
        super(message);
    }

    public UploadFileValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}