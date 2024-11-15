package com.henriquenascimento.demo.service;

import com.henriquenascimento.demo.dto.FileProcessRequestDTO;
import com.henriquenascimento.demo.dto.FileProcessResponseDTO;
import com.henriquenascimento.demo.dto.FileResponseDTO;
import com.henriquenascimento.demo.dto.UploadFileValidationDTO;
import com.henriquenascimento.demo.enumerator.FileProcessStatus;
import com.henriquenascimento.demo.exceptions.FileException;
import com.henriquenascimento.demo.factory.UploadFileValidationFactory;
import com.henriquenascimento.demo.mapper.FileProcessResponseMapper;
import com.henriquenascimento.demo.mapper.FileResponseMapper;
import com.henriquenascimento.demo.model.File;
import com.henriquenascimento.demo.model.FileProcess;
import com.henriquenascimento.demo.properties.AWSProperties;
import com.henriquenascimento.demo.properties.FileS3UploadProperties;
import com.henriquenascimento.demo.repository.FileProcessRepository;
import com.henriquenascimento.demo.repository.FileRepository;
import com.henriquenascimento.demo.utils.FileUtils;
import com.henriquenascimento.demo.utils.S3Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class S3Service {

    private final S3Client s3Client;
    private final AWSProperties awsProperties;
    private final FileProcessRepository fileProcessRepository;
    private final FileRepository fileRepository;
    private final FileUtils fileUtils;
    private final S3Utils s3Utils;
    private final UploadFileValidationFactory uploadFileValidationFactory;
    private final FileProcessResponseMapper fileProcessResponseMapper;
    private final FileResponseMapper fileResponseMapper;
    private final FileS3UploadProperties fileS3UploadProperties;
    public static final String FILE_NAME_SEPARATOR = "_";
    public static final String TEMP_FILE_PREFIX = "temp-";

    public FileProcessResponseDTO upload(final FileProcessRequestDTO fileProcessRequestDTO) {
        uploadFileValidationFactory.createValidationChain(null)
                .validate(UploadFileValidationDTO.builder()
                        .files(fileProcessRequestDTO.getFiles())
                        .fileProperties(fileS3UploadProperties)
                        .build());
        //TODO implement checkForVirus(fileProcessRequestDTO) (with interface)

        try {
            FileProcessResponseDTO fileProcessResponseDTO = fileProcessResponseMapper.toDTO(
                    fileProcessRepository.save(FileProcess.builder()
                            .status(FileProcessStatus.PROCESSING)
                            .description(fileProcessRequestDTO.getDescription())
                            .build()));

            fileProcessResponseDTO.setFilesFound((long) fileProcessRequestDTO.getFiles().size());
            fileProcessResponseDTO.setFilesSent(0L);
            // TODO refactory foreach
            for (MultipartFile multipartFile : fileProcessRequestDTO.getFiles()) {
                String fileName = UUID.randomUUID() + FILE_NAME_SEPARATOR + multipartFile.getOriginalFilename();
                Path tempFilePath = Files.createTempFile(TEMP_FILE_PREFIX, fileName);
                Files.copy(multipartFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(awsProperties.getS3().getBucketName())
                        .key(s3Utils.getFullPath(null, fileProcessRequestDTO.getPath(), fileName))
                        .acl(awsProperties.getS3().getAclDefault())
                        .build();
                s3Client.putObject(putObjectRequest, tempFilePath);
                Files.delete(tempFilePath);

                FileResponseDTO fileResponseDTO = fileResponseMapper.toDTO(
                        fileRepository.save(File.builder()
                                .fileProcess(fileProcessResponseMapper.toEntity(fileProcessResponseDTO))
                                .path(s3Utils.getPath(awsProperties.getS3().getBucketName(),
                                        fileProcessRequestDTO.getPath()))
                                .fileName(fileName)
                                .size(multipartFile.getSize())
                                .hash(fileUtils.getFileHash(multipartFile))
                                .mimeType(fileUtils.getMimeType(multipartFile))
                                .build()));

                updateFileProcess(fileProcessResponseDTO, fileResponseDTO);
            }
            return updateFileProcessResponseDTO(
                    finishFileProcess(fileProcessResponseDTO), fileProcessRequestDTO);
        } catch (Exception e) {
            log.error("Erro ao fazer upload de arquivos: {}", e.getMessage(), e);
            throw new FileException("Erro ao fazer upload de arquivos: " + e.getMessage(), e);
        }
    }

    private void updateFileProcess(final FileProcessResponseDTO fileProcessResponseDTO,
                                   final FileResponseDTO fileResponseDTO) {
        fileProcessResponseDTO.getFiles().add(fileResponseDTO);
        fileProcessResponseDTO.setFilesSent(fileProcessResponseDTO.getFilesSent() + 1);
        fileProcessRepository.save(
                fileProcessResponseMapper.toEntity(fileProcessResponseDTO));
    }

    private FileProcessResponseDTO finishFileProcess(final FileProcessResponseDTO fileProcessResponseDTO) {
        fileProcessResponseDTO.setStatus(FileProcessStatus.PROCESSED);
        fileProcessResponseDTO.setCompletionDate(Instant.now());

        return fileProcessResponseMapper.toDTO(
                fileProcessRepository.save(
                        fileProcessResponseMapper.toEntity(fileProcessResponseDTO)));
    }

    private FileProcessResponseDTO updateFileProcessResponseDTO(final FileProcessResponseDTO fileProcessResponseDTO,
                                                                final FileProcessRequestDTO fileProcessRequestDTO) {
        Optional.ofNullable(fileProcessResponseDTO.getFiles())
                .ifPresent(files -> files.forEach(file -> {
                    file.setFullPathFile(s3Utils.getFullPath(
                            s3Utils.getAwsProperties().getS3().getBucketName(),
                            fileProcessRequestDTO.getPath(),
                            file.getFileName())
                    );
                    file.setFileS3Url(s3Utils.getFileS3Url(fileProcessRequestDTO.getPath(), file.getFileName()));
                }));

        return fileProcessResponseDTO;
    }

    public ByteArrayResource downloadFile(final String path, final String fileName) {
        return new ByteArrayResource(s3Client.getObjectAsBytes(
                        GetObjectRequest.builder()
                                .bucket(awsProperties.getS3().getBucketName())
                                .key(s3Utils.getKey(path, fileName))
                                .build())
                .asByteArray());
    }

    public List<String> findAll() {
        return s3Client.listObjectsV2(
                        ListObjectsV2Request.builder()
                                .bucket(awsProperties.getS3().getBucketName())
                                .build())
                .contents().stream()
                .map(S3Object::key)
                .toList();
    }

    public void deleteFile(final String path, final String fileName) {
        try {
            final String key = s3Utils.getKey(path, fileName);
            s3Client.headObject(HeadObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucketName())
                    .key(key)
                    .build());

            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(awsProperties.getS3().getBucketName())
                    .key(key)
                    .build());
        } catch (NoSuchKeyException e) {
            log.error("File not found: {}", e.getMessage(), e);
            throw new FileException("File not found: " + e.getMessage(), e);
        }
    }

    public void renameFile(
            final String currentBucketName,
            final String newBucketName,
            final String currentFileName,
            final String newFileName,
            final String currentPath,
            final String newPath) {
        try {
            s3Client.copyObject(CopyObjectRequest.builder()
                    .sourceBucket(s3Utils.getBucketName(currentBucketName))
                    .sourceKey(s3Utils.getKey(currentPath, currentFileName))
                    .destinationBucket(s3Utils.getBucketName(newBucketName))
                    .destinationKey(s3Utils.getKey(newPath, newFileName))
                    .build());

            deleteFile(currentPath, currentFileName);
        } catch (Exception e) {
            log.error("Error renaming file: {}", e.getMessage(), e);
            throw new FileException("Error renaming file: " + e.getMessage(), e);
        }

    }

}
