package com.henriquenascimento.demo.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("demo-app-spring.file.s3-upload")
public class FileS3UploadProperties implements FileProperties {

    private Boolean enabled;
    private int maxFiles;
    private Long maxSizePerFile;
    private List<String> allowedMimeType;
    private String basePath;

}
