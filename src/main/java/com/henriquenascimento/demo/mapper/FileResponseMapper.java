package com.henriquenascimento.demo.mapper;

import com.henriquenascimento.demo.dto.FileResponseDTO;
import com.henriquenascimento.demo.model.File;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FileResponseMapper {

    FileResponseMapper INSTANCE = Mappers.getMapper(FileResponseMapper.class);

    @Mapping(target = "fullPathFile", ignore = true)
    FileResponseDTO toDTO(final File entity);

    List<FileResponseDTO> toListDTO(final List<File> entity);

    File toEntity(final FileResponseDTO dto);

    List<File> toListEntity(final List<FileResponseDTO> dto);

    @AfterMapping
    default void setFullPathFile(@MappingTarget FileResponseDTO dto, final File entity) {
        dto.setFullPathFile(entity.getPath() + "/" + entity.getFileName());
    }
}
