package com.henriquenascimento.demo.consumer;

import com.henriquenascimento.demo.dto.AwsSqsDemoRequestDTO;
import com.henriquenascimento.demo.service.AwsSqsDemoService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Map;

import static com.henriquenascimento.demo.constant.GlobalConstant.PREFIX_QUEUE_CONTAINER;
import static com.henriquenascimento.demo.constant.GlobalConstant.QUEUE_SQS_DEMO_TEST;

@Profile("!test")
@Component
@Log4j2
@RequiredArgsConstructor
public class SqsConsumer extends BaseSqsMessageListener {

    private final AwsSqsDemoService awsSqsDemoService;

    @SqsListener(value = QUEUE_SQS_DEMO_TEST,
            id = PREFIX_QUEUE_CONTAINER + QUEUE_SQS_DEMO_TEST,
            messageVisibilitySeconds = "${sqs.message-visibility-seconds}",
            maxConcurrentMessages = "${sqs.max-concurrent-messages}",
            pollTimeoutSeconds = "${sqs.poll-timeout-seconds}",
            maxMessagesPerPoll = "${sqs.max-messages-per-poll}"
    )
    public void receiveDemoRequest(@Payload final AwsSqsDemoRequestDTO message, @Headers final Map<String, String> headers) {
        var watch = new StopWatch();
        watch.start();
        log.debug(" <-- Received '{}'", message);
        awsSqsDemoService.processSolicitation(message, super.getRetryCount(headers));
        log.debug(" [x] Processor demo request {} processed", message);
        watch.stop();
        log.debug(" Received demo request {} done in {}s", message, watch.getTotalTimeSeconds());
    }

}
