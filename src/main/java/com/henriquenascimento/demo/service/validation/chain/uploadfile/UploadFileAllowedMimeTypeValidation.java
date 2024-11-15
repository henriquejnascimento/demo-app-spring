package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UploadFileAllowedMimeTypeValidation implements UploadFileValidation {

    @Override
    public void validate(final UploadFileValidationDTO uploadFileValidationDTO) {
        for (MultipartFile file : uploadFileValidationDTO.getFiles()) {
            if (!uploadFileValidationDTO.getFileProperties().getAllowedMimeType().contains(file.getContentType())) {
                throw new UploadFileValidationException("File type not allowed: " + file.getContentType());
            }
        }
    }

}
