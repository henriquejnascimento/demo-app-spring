package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.exceptions.UploadFileValidationException;
import com.henriquenascimento.demo.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class UploadFileMaxSizePerFileValidation implements UploadFileValidation {

    private final FileProperties fileProperties;

    @Override
    public void validate(final FileRequestDTO fileRequestDTO) {
        for (MultipartFile file : fileRequestDTO.getFiles()) {
            if (file.getSize() > fileProperties.getMaxSizePerFile()) {
                throw new UploadFileValidationException(
                        String.format("The file '%s' exceeds the maximum allowed size of %d bytes.",
                                file.getOriginalFilename(),
                                fileProperties.getMaxSizePerFile()));
            }
        }
    }

}
