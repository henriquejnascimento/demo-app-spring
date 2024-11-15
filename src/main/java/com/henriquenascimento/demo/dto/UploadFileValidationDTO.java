package com.henriquenascimento.demo.dto;

import com.henriquenascimento.demo.properties.FileProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileValidationDTO {

    private List<MultipartFile> files;
    private FileProperties fileProperties;

}
