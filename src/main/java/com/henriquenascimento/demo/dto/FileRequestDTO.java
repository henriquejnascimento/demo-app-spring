package com.henriquenascimento.demo.dto;

import com.henriquenascimento.demo.constant.GlobalConstant;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequestDTO {

    @NotEmpty(message = GlobalConstant.REQUIRED_FIELD)
    private List<MultipartFile> files;
    private String path;
    private String description;

}
