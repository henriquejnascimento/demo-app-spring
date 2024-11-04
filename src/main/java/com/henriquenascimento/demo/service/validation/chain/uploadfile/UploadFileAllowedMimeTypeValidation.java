package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import com.henriquenascimento.demo.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UploadFileAllowedMimeTypeValidation implements UploadFileValidation {

    private final FileProperties fileProperties;

    @Override
    public void validate(final FileRequestDTO fileRequestDTO) {
        for (MultipartFile file : fileRequestDTO.getFiles()) {
            if (!fileProperties.getAllowedMimeType().contains(file.getContentType())) {
                throw new UploadFileValidationException("File type not allowed: " + file.getContentType());
            }
        }
    }

}
