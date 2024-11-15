package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UploadFileEnabledValidation implements UploadFileValidation {

    @Override
    public void validate(final UploadFileValidationDTO uploadFileValidationDTO) {
        if (Boolean.FALSE.equals(uploadFileValidationDTO.getFileProperties().getEnabled())) {
            throw new UploadFileValidationException("Uploads are currently disabled.");
        }
    }
}
