package com.henriquenascimento.demo.utils;

import com.henriquenascimento.demo.properties.SqsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SqsUtils {

    private final SqsProperties sqsProperties;

    public boolean hasExceededRetryCount(final Long retryCount) {
        return retryCount >= sqsProperties.getMessageRetries();
    }

}
