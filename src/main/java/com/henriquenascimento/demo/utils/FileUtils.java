package com.henriquenascimento.demo.utils;

import com.henriquenascimento.demo.exceptions.FileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Optional;

@Component
public class FileUtils {

    public String getMimeType(final MultipartFile multipartFile) {
        return Optional.ofNullable(multipartFile.getContentType())
                .orElse("application/octet-stream");
    }

    public String getFileHash(MultipartFile multipartFile) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream inputStream = multipartFile.getInputStream()) {
                byte[] buffer = new byte[8192]; // Reads in 8KB blocks
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            return byteArrayToHex(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new FileException("Could not calculate file hash: " + e.getMessage(), e);
        }
    }

    private String byteArrayToHex(byte[] byteArray) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : byteArray) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }

}
