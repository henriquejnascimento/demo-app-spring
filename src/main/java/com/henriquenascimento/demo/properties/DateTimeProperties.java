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
@ConfigurationProperties("demo-app-spring.date-time")
public class DateTimeProperties {

    private String zoneId;
    private String dateFormat;
    private String dateTimeFormat;

}
