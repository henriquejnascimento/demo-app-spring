package com.henriquenascimento.demo.dto;

import com.henriquenascimento.demo.enumerator.FileProcessStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileProcessResponseDTO extends BaseDTO {

    private Long id;
    private FileProcessStatus status;
    private String description;
    private Long filesFound;
    private Long filesSent;
    private Instant completionDate;

    @Builder.Default
    private List<FileResponseDTO> files = new ArrayList<>();

}
