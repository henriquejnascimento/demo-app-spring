package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UploadFileMaxSizePerFileValidation implements UploadFileValidation {

    @Override
    public void validate(final UploadFileValidationDTO uploadFileValidationDTO) {
        for (MultipartFile file : uploadFileValidationDTO.getFiles()) {
            if (file.getSize() > uploadFileValidationDTO.getFileProperties().getMaxSizePerFile()) {
                throw new UploadFileValidationException(
                        String.format("The file '%s' exceeds the maximum allowed size of %d bytes.",
                                file.getOriginalFilename(),
                                uploadFileValidationDTO.getFileProperties().getMaxSizePerFile()));
            }
        }
    }

}
