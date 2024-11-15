package com.henriquenascimento.demo.utils;

import com.henriquenascimento.demo.properties.AWSProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Log4j2
@Data
public class S3Utils {

    private final AWSProperties awsProperties; // TODO REMOVE
    private static final String FILE_SEPARATOR_S3 = "/";
    private static final String S3_URL_PREFIX = "https://";
    private static final String S3_DOMAIN_SUFFIX = ".s3.amazonaws.com/";

    public String getFullPath(final String bucketName, final String path, final String fileName) {
        StringBuilder fullPath = new StringBuilder();

        appendWithSlash(fullPath, bucketName);
        appendWithSlash(fullPath, path);

        return (ObjectUtils.isEmpty(fileName)) ? fullPath.toString() : fullPath.append(fileName).toString();
    }

    public String getFileS3Url(final String path, final String fileName) {
        StringBuilder finalPath = new StringBuilder();
        appendWithSlash(finalPath, path);
        return S3_URL_PREFIX + awsProperties.getS3().getBucketName() + S3_DOMAIN_SUFFIX + finalPath.append(fileName);
    }

    public String getPath(final String bucketName, final String path) {
        return getFullPath(bucketName, path, null);
    }

    private void appendWithSlash(final StringBuilder builder, final String value) {
        if (!ObjectUtils.isEmpty(value)) {
            builder.append(value);
            if (!value.endsWith(FILE_SEPARATOR_S3)) {
                builder.append(FILE_SEPARATOR_S3);
            }
        }
    }

    public String formatPath(String path) {
        if (ObjectUtils.isEmpty(path)) {
            return "";
        }
        if (path.startsWith(FILE_SEPARATOR_S3)) {
            path = path.substring(1);
        }
        if (!path.endsWith(FILE_SEPARATOR_S3)) {
            path = path + FILE_SEPARATOR_S3;
        }
        return path;
    }

    public String getKey(final String path, final String fileName) {
        return formatPath(path) + fileName;
    }

    public String getBucketName(final String bucketName) {
        return (ObjectUtils.isEmpty(bucketName)) ? awsProperties.getS3().getBucketName() : bucketName;
    }

}
