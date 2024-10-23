package com.henriquenascimento.demo.mapper;

import com.henriquenascimento.demo.dto.ProductRequestDTO;
import com.henriquenascimento.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {

    ProductRequestMapper INSTANCE = Mappers.getMapper(ProductRequestMapper.class);

    ProductRequestDTO toDTO(final Product entity);

    List<ProductRequestDTO> toListDTO(final List<Product> entity);

    @Mapping(target = "id", ignore = true)
    Product toEntity(final ProductRequestDTO dto);

    List<Product> toListEntity(final List<ProductRequestDTO> dto);

}
