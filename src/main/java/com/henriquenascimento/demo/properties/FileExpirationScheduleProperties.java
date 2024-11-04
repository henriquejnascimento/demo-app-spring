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
@ConfigurationProperties("demo-app-spring.schedule.upload-file-expiration")
public class FileExpirationScheduleProperties {

    private boolean enabled;
    private String cron;
    private Integer days;

}
