package com.henriquenascimento.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {

    private long timestamp;
    private int httpStatus;
    private String errorCode;
    private String errorMessage;
    private String method;
    private String path;

}
