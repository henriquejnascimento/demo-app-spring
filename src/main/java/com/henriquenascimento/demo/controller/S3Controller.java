package com.henriquenascimento.demo.controller;

import com.henriquenascimento.demo.dto.FileProcessRequestDTO;
import com.henriquenascimento.demo.dto.FileProcessResponseDTO;
import com.henriquenascimento.demo.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<FileProcessResponseDTO> uploadFile(
            @Valid final FileProcessRequestDTO fileProcessRequestDTO) {
        return new ResponseEntity<>(s3Service.upload(fileProcessRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable("fileName") final String fileName,
            @RequestParam(value = "path", required = false) final String path) {
        ByteArrayResource fileBytes = s3Service.downloadFile(path, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    @GetMapping
    public ResponseEntity<List<String>> findAll() {
        List<String> files = s3Service.findAll();
        return ResponseEntity.ok(files);
    }

    @PutMapping("/rename-or-move-file")
    public ResponseEntity<String> renameOrMoveFile(
            @RequestParam(value = "currentBucketName", required = false) final String currentBucketName,
            @RequestParam(value = "newBucketName", required = false) final String newBucketName,
            @RequestParam(value = "currentFileName") final String currentFileName,
            @RequestParam(value = "newFileName") final String newFileName,
            @RequestParam(value = "currentPath", required = false) final String currentPath,
            @RequestParam(value = "newPath", required = false) final String newPath) {
        s3Service.renameOrMoveFile(currentBucketName, newBucketName, currentFileName, newFileName, currentPath, newPath);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-file/{fileName}")
    public ResponseEntity<String> deleteFile(
            @PathVariable final String fileName,
            @RequestParam(value = "bucketName", required = false) final String bucketName,
            @RequestParam(value = "path", required = false) final String path) {
        s3Service.deleteFile(bucketName, path, fileName);
        return ResponseEntity.noContent().build();
    }

}
