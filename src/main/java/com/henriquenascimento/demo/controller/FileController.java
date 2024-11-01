package com.henriquenascimento.demo.controller;

import com.henriquenascimento.demo.dto.FileResponseDTO;
import com.henriquenascimento.demo.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.henriquenascimento.demo.controller.FileController.BASE_URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
@Tag(name = "File", description = "Manage files. Upload, download, list files, get file info, rename and delete")
public class FileController {

    static final String BASE_URI = "/file";
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<FileResponseDTO>> uploadFile(
            @RequestParam(value = "path", required = false) final String path,
            @RequestParam(value = "description", required = false) final String description,
            @RequestParam("file") final List<MultipartFile> multipartFiles) {
        return new ResponseEntity<>(fileService.uploadFile(path, description, multipartFiles), HttpStatus.OK);
    }

    @GetMapping("/download/{idFileStorage}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("idFileStorage") final Long idFileStorage) {
        Resource resource = fileService.downloadFile(idFileStorage);
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    @PatchMapping("/rename/{idFileStorage}")
    public ResponseEntity<FileResponseDTO> renameFile(
            @PathVariable("idFileStorage") final Long idFileStorage,
            @RequestParam("newFileName") final String newFileName) {
        return new ResponseEntity<>(fileService.renameFile(idFileStorage, newFileName), HttpStatus.OK);
    }

    @DeleteMapping("{idFileStorage}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable("idFileStorage") final Long idFileStorage) {
        fileService.deleteFile(idFileStorage);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<FileResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "size", defaultValue = "10") final int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") final String[] sort) {
        // TODO ADD PATH param (list from path)
        return new ResponseEntity<>(fileService.findAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{idFileStorage}")
    public ResponseEntity<FileResponseDTO> findById(
            @PathVariable("idFileStorage") final Long idFileStorage) {
        FileResponseDTO fileResponseDTO = fileService.findById(idFileStorage);
        return new ResponseEntity<>(fileResponseDTO, HttpStatus.OK);
    }
}
