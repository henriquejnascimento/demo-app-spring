package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import com.henriquenascimento.demo.properties.FileProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileEnabledValidation implements UploadFileValidation {

    private final FileProperties fileProperties;

    @Override
    public void validate(final FileRequestDTO fileRequestDTO) {
        if (Boolean.FALSE.equals(fileProperties.getEnabled())) {
            throw new UploadFileValidationException("Uploads are currently disabled.");
        }
    }
}
