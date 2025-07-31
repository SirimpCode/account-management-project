package com.github.accountmanagementproject.service.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.github.accountmanagementproject.common.exceptions.CustomBadRequestException;
import com.github.accountmanagementproject.common.exceptions.CustomBindException;
import com.github.accountmanagementproject.common.exceptions.CustomNotFoundException;
import com.github.accountmanagementproject.common.exceptions.CustomServerException;
import com.github.accountmanagementproject.web.dto.storage.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final BlobContainerClient containerClient;
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 일반 첨부 10MB 제한


    //파일 업로드 및 원본파일명, URL 반환
    public List<FileDto> filesUploadAndGetUrls(List<MultipartFile> multipartFiles, boolean isBigFile, String directory) {
        return multipartFiles.parallelStream()
                .map(file -> uploadSingleFile(file, isBigFile, directory))
                .toList();
    }

    private FileDto uploadSingleFile(MultipartFile file, boolean isBigFile, String directory) {
        validateFile(file);
        String storageFileName = generateUniqueFileName(file.getOriginalFilename());
        BlobClient blobClient = uploadToBlob(file, directory+"/"+storageFileName);
        return createFileDto(file.getOriginalFilename(), blobClient.getBlobUrl());
    }

    private FileDto createFileDto(String originalFilename, String blobUrl) {
        return FileDto.of(originalFilename,URLDecoder.decode(blobUrl, StandardCharsets.UTF_8));
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw CustomBadRequestException.of().customMessage("File is empty").build();
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw CustomBindException.of().customMessage("File size exceeds maximum limit")
                    .request(file.getSize()).build();
        }
    }


    private BlobClient uploadToBlob(MultipartFile file, String filePath) {
        BlobClient blobClient = containerClient.getBlobClient(filePath);
        try {
            blobClient.upload(file.getInputStream(), file.getSize(), false);
            return blobClient;
        }catch (IOException ioException){
            throw CustomServerException.of().systemMessage(ioException.getMessage()).request(file.getOriginalFilename()).customMessage("File Upload Failed").build();
        }
    }

    //업로드 고유 파일명 생성
    private String generateUniqueFileName(String originalFileName) {

        String extension = Optional.ofNullable(originalFileName)
                .filter(f -> f.lastIndexOf(".") > 0)//마지막 .의 위치가 0보다 클경우 (.으로 시작하고 그뒤에 .이 없을경우 확장자가 없는걸로 처리됨)
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .orElse("");
        return UUID.randomUUID() + "." + extension;
    }

    //업로드 취소 (삭제)
    public void deleteFiles(List<String> fileUrls) {

        hasDeleteUrlsDuplicates(fileUrls);

        List<BlobClient> blobClients = fileUrls.parallelStream()
                .map(this::extractBlobNameFromUrl)
                .map(this::mapToBlobClient)
                .peek(this::validateBlobExists)
                .toList();

        blobClients.parallelStream().forEach(this::deleteSingleFile);

    }
    private void hasDeleteUrlsDuplicates(List<String> fileUrls) {
        if(fileUrls.size() != new HashSet<>(fileUrls).size())
            throw CustomBadRequestException.of().customMessage("삭제 요청된 URL들 중 중복된 URL이 존재합니다.").request(fileUrls).build();
    }

    private void validateBlobExists(BlobClient blobClient) {
        if (!blobClient.exists()) {
            throw CustomNotFoundException.of()
                    .systemMessage("Blob not found")
                    .customMessage("파일을 찾을 수 없습니다.")
                    .request(blobClient.getBlobName()).build();
        }

    }

    private BlobClient mapToBlobClient(String string) {
        return containerClient.getBlobClient(string);
    }

    private void deleteSingleFile(BlobClient blobClient) {
        try {
            blobClient.delete();
        }catch (BlobStorageException e){
            throw CustomServerException.of().systemMessage(e.getMessage()).request(blobClient.getBlobName()).customMessage("파일 삭제 실패").build();
        }

    }

    private String extractBlobNameFromUrl(String url) {
        String containerName = containerClient.getBlobContainerName();
        if (url == null || !url.contains(containerName)) throw CustomBadRequestException.of().customMessage("올바르지 않은 파일 URL").request(url).build();
        return url.substring(url.indexOf(containerName)+containerName.length()+1);
    }

}
