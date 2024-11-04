package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileNotEmptyFileValidation implements UploadFileValidation {

    @Override
    public void validate(final FileRequestDTO fileRequestDTO) {
        if (ObjectUtils.isEmpty(fileRequestDTO.getFiles())) {
            throw new UploadFileValidationException("No files provided.");
        } else {
            for (MultipartFile file : fileRequestDTO.getFiles()) {
                if (file.getSize() == 0) {
                    throw new UploadFileValidationException("File size cannot be 0 bytes.");
                }
                if (ObjectUtils.isEmpty(file.getOriginalFilename())) {
                    throw new UploadFileValidationException("The file name cannot be null or empty.");
                }
            }
        }
    }

}
