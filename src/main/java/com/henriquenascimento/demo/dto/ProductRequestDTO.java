package com.henriquenascimento.demo.dto;

import com.henriquenascimento.demo.constant.GlobalConstant;
import com.henriquenascimento.demo.enumerator.ProductStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotEmpty(message = GlobalConstant.REQUIRED_FIELD)
    @Size(min = 1, max = 20)
    private String name;

    @NotEmpty(message = GlobalConstant.REQUIRED_FIELD)
    @Size(min = 1, max = 60)
    private String description;

    private ProductStatus status;

}
