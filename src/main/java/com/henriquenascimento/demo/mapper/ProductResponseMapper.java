package com.henriquenascimento.demo.mapper;

import com.henriquenascimento.demo.dto.ProductResponseDTO;
import com.henriquenascimento.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    ProductResponseMapper INSTANCE = Mappers.getMapper(ProductResponseMapper.class);

    ProductResponseDTO toDTO(final Product entity);

    List<ProductResponseDTO> toListDTO(final List<Product> entity);

    Product toEntity(final ProductResponseDTO dto);

    List<Product> toListEntity(final List<ProductResponseDTO> dto);

}
