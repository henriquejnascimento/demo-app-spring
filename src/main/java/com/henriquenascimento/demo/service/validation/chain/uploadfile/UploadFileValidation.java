package com.henriquenascimento.demo.service.validation.chain.uploadfile;

import com.henriquenascimento.demo.dto.FileRequestDTO;

public interface UploadFileValidation {

    void validate(final FileRequestDTO fileRequestDTO);

}
