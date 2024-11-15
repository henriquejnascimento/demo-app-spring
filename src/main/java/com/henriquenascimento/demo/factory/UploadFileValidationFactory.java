package com.henriquenascimento.demo.factory;

import com.henriquenascimento.demo.service.validation.chain.uploadfile.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UploadFileValidationFactory {

    private final List<UploadFileValidation> validations;

    public UploadFileValidationChain createValidationChain(final List<UploadFileValidation> customValidation) {
        return (ObjectUtils.isEmpty(customValidation)) ?
             new UploadFileValidationChain(Arrays.asList(
                    new UploadFileEnabledValidation(),
                    new UploadFileAllowedMimeTypeValidation(),
                    new UploadFileMaxFilesValidation(),
                    new UploadFileMaxSizePerFileValidation(),
                    new UploadFileNotEmptyFileValidation()
            ))
        : new UploadFileValidationChain(customValidation);
    }

}
