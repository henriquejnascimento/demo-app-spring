package com.henriquenascimento.demo.consumer;

import org.springframework.messaging.handler.annotation.Headers;

import java.util.Map;
import java.util.Optional;

public class BaseSqsMessageListener {

    static final String LOG_RECEIVED = " <-- Received '";
    static final String LOG_PROCESSED = " processed";
    static final String LOG_DONE_IN = " done in ";

    protected Long getRetryCount(@Headers final Map<String, String> headers) {
        return Long.valueOf(Optional.ofNullable(headers)
                .map(header -> header.get("Sqs_Msa_ApproximateReceiveCount"))
                .orElse("1"));
    }

}