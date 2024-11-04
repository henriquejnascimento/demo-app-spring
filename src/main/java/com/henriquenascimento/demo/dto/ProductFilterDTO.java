package com.henriquenascimento.demo.dto;

import com.henriquenascimento.demo.enumerator.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDTO {

    private String name;
    private String description;
    private ProductStatus status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Example 1
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Example 2
    private LocalDate endDate;

}
