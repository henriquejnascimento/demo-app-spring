package com.henriquenascimento.demo.exceptions;

public class RejectAndDontRequeueException extends RuntimeException {

    public RejectAndDontRequeueException(Throwable cause) {
        super(cause);
    }

}
