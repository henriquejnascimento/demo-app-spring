package com.henriquenascimento.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {

    private Timestamp createdAt;
    private Timestamp updatedAt;

}
