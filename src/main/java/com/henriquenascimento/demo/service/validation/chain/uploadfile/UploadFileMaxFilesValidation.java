package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import com.henriquenascimento.demo.properties.FileProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileMaxFilesValidation implements UploadFileValidation {

    private final FileProperties fileProperties;

    @Override
    public void validate(final FileRequestDTO fileRequestDTO) {
        if (fileRequestDTO.getFiles().size() > fileProperties.getMaxFiles()) {
            throw new UploadFileValidationException("Exceeded maximum number of files: " + fileProperties.getMaxFiles());
        }
    }
}
