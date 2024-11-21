package com.henriquenascimento.demo.producer;

import com.henriquenascimento.demo.constant.GlobalConstant;
import com.henriquenascimento.demo.dto.AwsSqsDemoRequestDTO;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SqsProducer {

    private final SqsTemplate sqsTemplate;

    public void sendSqsDemoTest(final AwsSqsDemoRequestDTO request) {
        try {
            sqsTemplate.sendAsync(GlobalConstant.QUEUE_SQS_DEMO_TEST, request);
            log.debug(" --> SQS demo test Sent '{}'", request);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
