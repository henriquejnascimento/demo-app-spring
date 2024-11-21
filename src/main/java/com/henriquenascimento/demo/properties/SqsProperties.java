package com.henriquenascimento.demo.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("sqs")
public class SqsProperties {

    private Integer messageVisibilitySeconds;
    private Integer maxConcurrentMessages;
    private Integer pollTimeoutSeconds;
    private Integer maxMessagesPerPoll;
    private Integer messageRetries;
    private Integer ttl;

}
