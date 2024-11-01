package com.henriquenascimento.demo.mapper;

import com.henriquenascimento.demo.dto.FileResponseDTO;
import com.henriquenascimento.demo.model.File;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FileResponseMapper {

    FileResponseMapper INSTANCE = Mappers.getMapper(FileResponseMapper.class);

    FileResponseDTO toDTO(final File entity);

    List<FileResponseDTO> toListDTO(final List<File> entity);

    File toEntity(final FileResponseDTO dto);

    List<File> toListEntity(final List<FileResponseDTO> dto);

}
