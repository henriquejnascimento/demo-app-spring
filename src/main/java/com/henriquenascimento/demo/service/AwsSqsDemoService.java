package com.henriquenascimento.demo.service;

import com.henriquenascimento.demo.dto.AwsSqsDemoRequestDTO;
import com.henriquenascimento.demo.exceptions.RejectAndDontRequeueException;
import com.henriquenascimento.demo.producer.SqsProducer;
import com.henriquenascimento.demo.utils.SqsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AwsSqsDemoService {

    private final SqsProducer sqsProducer;
    private final SqsUtils sqsUtils;

    public void sendDemoMessage(final AwsSqsDemoRequestDTO request) {
        log.info("Sending demo message to SQS: {}", request);
        sqsProducer.sendSqsDemoTest(request);
    }

    public void processSolicitation(final AwsSqsDemoRequestDTO request, final Long retryCount) {
        log.info("Starting solicitation processing: {}, Retry count: {}", request, retryCount);

        try {
            // Any demo business logic here

            log.info("Solicitation processed successfully.");
        } catch (Exception e) {
            log.error("Error on process solicitation demo test", e);
            if (!sqsUtils.hasExceededRetryCount(retryCount)) {
                log.warn("Retrying solicitation: {}, Retry count: {}", request, retryCount + 1);
                throw new RejectAndDontRequeueException(e);
            } else {
                log.error("Exceeded retry count for solicitation: {}", request);
            }
        }
    }

}
