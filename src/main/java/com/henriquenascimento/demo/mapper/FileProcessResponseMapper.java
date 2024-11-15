package com.henriquenascimento.demo.mapper;

import com.henriquenascimento.demo.dto.FileProcessResponseDTO;
import com.henriquenascimento.demo.model.FileProcess;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FileProcessResponseMapper {

    FileProcessResponseMapper INSTANCE = Mappers.getMapper(FileProcessResponseMapper.class);

    FileProcessResponseDTO toDTO(final FileProcess entity);

    List<FileProcessResponseDTO> toListDTO(final List<FileProcess> entity);

    FileProcess toEntity(final FileProcessResponseDTO dto);

    List<FileProcess> toListEntity(final List<FileProcessResponseDTO> dto);

}
