package com.henriquenascimento.demo.configuration;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfig {

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
        messageConverter.setPayloadTypeMapper(m -> null);

        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options.messageConverter(messageConverter))
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

}
