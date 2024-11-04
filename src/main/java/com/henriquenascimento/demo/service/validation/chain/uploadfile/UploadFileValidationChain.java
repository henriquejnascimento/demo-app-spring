package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UploadFileValidationChain {

    private final List<UploadFileValidation> validations;

    public void validate(final FileRequestDTO fileRequestDTO) {
        for (UploadFileValidation validation : validations) {
            validation.validate(fileRequestDTO);
        }
    }

}
