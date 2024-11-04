package com.henriquenascimento.demo.service;

import com.henriquenascimento.demo.dto.FileRequestDTO;
import com.henriquenascimento.demo.dto.FileResponseDTO;
import com.henriquenascimento.demo.exceptions.FileException;
import com.henriquenascimento.demo.mapper.FileResponseMapper;
import com.henriquenascimento.demo.model.File;
import com.henriquenascimento.demo.properties.FileExpirationScheduleProperties;
import com.henriquenascimento.demo.properties.FileProperties;
import com.henriquenascimento.demo.repository.FileRepository;
import com.henriquenascimento.demo.service.validation.chain.uploadfile.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.henriquenascimento.demo.constant.ErrorConstant.buildFileNotFoundErrorMessage;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileService {

    private final FileProperties fileProperties;
    private final FileRepository fileRepository;
    private final FileResponseMapper fileResponseMapper;
    private final FileExpirationScheduleProperties fileExpirationScheduleProperties;

    public List<FileResponseDTO> fileUpload(final FileRequestDTO fileRequestDTO) {
        fileUploadValidation(fileRequestDTO);

        List<FileResponseDTO> fileResponseDTOList = new ArrayList<>();
        final String basePath = fileProperties.getBasePath() + UUID.randomUUID();
        fileRequestDTO.getFiles().forEach(multipartFile -> {
            final String fileName = multipartFile.getOriginalFilename();
            final String fullPath = !ObjectUtils.isEmpty(fileRequestDTO.getPath()) ? Paths.get(basePath, fileRequestDTO.getPath()).toString() : Paths.get(basePath).toString();
            saveFile(fullPath, fileName, multipartFile);

            fileResponseDTOList.add(
                    fileResponseMapper.toDTO(
                            fileRepository.save(
                                    File.builder()
                                            .path(fullPath)
                                            .fileName(fileName)
                                            .size(multipartFile.getSize())
                                            .hash(calculateFileHash(multipartFile))
                                            .mimeType(getMimeType(multipartFile))
                                            .description(fileRequestDTO.getDescription())
                                            .build())));
        });
        return fileResponseDTOList;
    }

    public Resource downloadFile(final Long idFileStorage) {
        try {
            File file = fileRepository.findById(idFileStorage)
                    .orElseThrow(() -> new FileException(buildFileNotFoundErrorMessage(idFileStorage)));
            Resource resource = getFileAsResource(file.getPath(), file.getFileName());
            if (ObjectUtils.isEmpty(resource) || !resource.exists()) {
                throw new FileException(buildFileNotFoundErrorMessage(idFileStorage));
            }
            return resource;
        } catch (FileException e) {
            throw new FileException("Error downloading file: " + e.getMessage(), e);
        }
    }

    public void saveFile(final String path, final String fileName, final MultipartFile multipartFile) {
        Path uploadPath = Paths.get(path);
        createDirectoryIfNotExists(uploadPath);
        copyFileToDirectory(uploadPath, fileName, multipartFile);
    }

    public void createDirectoryIfNotExists(Path uploadPath) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new FileException("Could not create upload directory: " + uploadPath, e);
        }
    }

    private void copyFileToDirectory(Path uploadPath, String fileName, MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new FileException("Could not save file: " + fileName, ioe);
        }
    }

    public Resource getFileAsResource(final String path, final String fileName) {
        Path filePath = Paths.get(path, fileName);
        if (!Files.exists(filePath)) {
            throw new FileException("File not found: " + fileName);
        }

        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new FileException("Failed to create URL resource for file: " + fileName, e);
        }
    }

    public FileResponseDTO renameFile(final Long idFileStorage, final String newFileName) {
        final File file = fileRepository.findById(idFileStorage)
                .orElseThrow(() -> new FileException("File not found with id: " + idFileStorage));

        final String oldFileName = file.getFileName();
        final String directoryPath = file.getPath();
        try {

            Files.move(Paths.get(directoryPath, oldFileName),
                    Paths.get(directoryPath, newFileName),
                    StandardCopyOption.REPLACE_EXISTING);

            file.setFileName(newFileName);
            fileRepository.save(file);
            return fileResponseMapper.toDTO(file);
        } catch (IOException e) {
            throw new FileException("Failed to rename file: " + oldFileName, e);
        }
    }

    public void deleteFile(final Long idFileStorage) {
        log.debug("Deleting file id: {}", idFileStorage);
        File file = fileRepository.findById(idFileStorage)
                .orElseThrow(() -> new FileException(buildFileNotFoundErrorMessage(idFileStorage)));

        try {
            Path filePath = Paths.get(file.getPath(), file.getFileName());
            Files.deleteIfExists(filePath);
            fileRepository.delete(file);
        } catch (IOException e) {
            throw new FileException("Could not delete file: " + file.getFileName(), e);
        }
    }

    private String calculateFileHash(MultipartFile multipartFile) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            try (InputStream inputStream = multipartFile.getInputStream()) {
                byte[] buffer = new byte[8192]; // Reads in 8KB blocks
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    digest.update(buffer, 0, bytesRead);
                }
            }
            return byteArrayToHex(digest.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new FileException("Could not calculate file hash: " + e.getMessage(), e);
        }
    }

    private String byteArrayToHex(byte[] byteArray) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : byteArray) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }

    public String getMimeType(MultipartFile multipartFile) {
        return Optional.ofNullable(multipartFile.getContentType())
                .orElse("application/octet-stream");
    }

    public Page<FileResponseDTO> findAll(
            final int page,
            final int size,
            final String[] sort) {
        return fileRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(sort[1]), sort[0]))
                .map(fileResponseMapper::toDTO);
    }

    public FileResponseDTO findById(final Long idFileStorage) {
        File file = fileRepository.findById(idFileStorage)
                .orElseThrow(() -> new FileException("File not found with ID: " + idFileStorage));
        return fileResponseMapper.toDTO(file);
    }

    public void fileUploadValidation(final FileRequestDTO fileRequestDTO) {
        new UploadFileValidationChain(Arrays.asList(
                new UploadFileEnabledValidation(fileProperties),
                new UploadFileAllowedMimeTypeValidation(fileProperties),
                new UploadFileMaxFilesValidation(fileProperties),
                new UploadFileMaxSizePerFileValidation(fileProperties),
                new UploadFileNotEmptyFileValidation()
        )).validate(fileRequestDTO);
    }

    public void purgeExpiredFiles() {
        log.debug("Starting expired files purged...");
        List<File> filesExpired = fileRepository.findAllByCreatedAtLessThan(Timestamp.from(
                Instant.now().minus(fileExpirationScheduleProperties.getDays(), ChronoUnit.DAYS)));
        log.debug("Expired files found: {}", filesExpired.size());
        AtomicReference<Long> fileCount = new AtomicReference<>((long) 0);
        filesExpired.forEach(file -> {
            fileCount.getAndSet(fileCount.get() + 1);
            log.debug("Deleting file {}/{}: {}", fileCount, filesExpired.size(), file);
            deleteFile(file.getId());
            fileRepository.delete(file);
        });
        //deleteEmptyDirectories(fileProperties.getBasePath()); // TODO implement
        log.debug("Expired files purged finished.");
    }

}
