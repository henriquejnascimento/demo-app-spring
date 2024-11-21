package com.henriquenascimento.demo.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final AwsConfig awsConfig;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(awsConfig.getRegion()))
                .credentialsProvider(ProfileCredentialsProvider.builder()
                        .profileName(awsConfig.getActiveProfile())
                        .build())
                .build();
    }


}
