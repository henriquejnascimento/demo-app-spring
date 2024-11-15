package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileMaxFilesValidation implements UploadFileValidation {

    @Override
    public void validate(final UploadFileValidationDTO uploadFileValidationDTO) {
        if (uploadFileValidationDTO.getFiles().size() > uploadFileValidationDTO.getFileProperties().getMaxFiles()) {
            throw new UploadFileValidationException("Exceeded maximum number of files: " + uploadFileValidationDTO.getFileProperties().getMaxFiles());
        }
    }
}
