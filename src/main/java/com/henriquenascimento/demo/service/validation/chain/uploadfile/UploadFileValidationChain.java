package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UploadFileValidationChain {

    private final List<UploadFileValidation> validations;

    public void validate(final UploadFileValidationDTO uploadFileValidationDTO) {
        for (UploadFileValidation validation : validations) {
            validation.validate(uploadFileValidationDTO);
        }
    }

}
