package com.henriquenascimento.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileResponseDTO extends BaseDTO {

    private Long id;
    private String path;
    private String fileName;
    private String fullPathFile;
    private String fileS3Url;
    private Long size;
    private String hash;
    private String mimeType;
    private String description;
//    private User uploadedBy;
//    private FileStatus status;
//    private Long originalSize;
//    private String tags;

}
