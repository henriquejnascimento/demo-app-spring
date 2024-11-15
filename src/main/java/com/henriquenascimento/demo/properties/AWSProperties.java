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
@ConfigurationProperties("cloud.aws")
public class AWSProperties {

    private String region;
    private Credentials credentials;
    private S3 s3;

    @Data
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Data
    public static class S3 {
        private String bucketName;
        private String aclDefault;
    }

}
