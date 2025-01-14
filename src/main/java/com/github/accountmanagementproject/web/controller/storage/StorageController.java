package com.github.accountmanagementproject.web.controller.storage;


import com.github.accountmanagementproject.service.storage.StorageService;
import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import com.github.accountmanagementproject.web.dto.storage.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController implements StorageControllerDocs {
    private final StorageService storageService;

    //한개 또는 여러개 업로드
    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomSuccessResponse<List<FileDto>>> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> multipartFiles,
                                                                              @RequestParam String directory,
                                                                              @RequestParam(required = false, name = "isBig") boolean isBigFile) {
        CustomSuccessResponse<List<FileDto>> response = CustomSuccessResponse.of(
                        HttpStatus.CREATED, "파일 업로드 성공",
                        storageService.filesUploadAndGetUrls(multipartFiles, isBigFile, directory)
        );

        return new ResponseEntity<>(response, response.getHttpStatus());
    }


    @DeleteMapping//여러개 파일 업로드 취소 (삭제)
    public CustomSuccessResponse<Void> deleteMultipleFiles(@RequestParam(value = "delete-urls") List<String> fileUrls) {
        storageService.deleteFiles(fileUrls);
        return CustomSuccessResponse.emptyDataOk("파일 삭제 성공");
    }

    //여러개 파일 수정
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomSuccessResponse<List<FileDto>> modifyMultipleFiles(@RequestParam(value = "delete-urls") List<String> deleteFileUrls,
                                                     @RequestPart("files") List<MultipartFile> multipartFiles,
                                                     @RequestParam String directory,
                                                     @RequestParam(required = false, name = "big") boolean isBigFile) {
        List<FileDto> response = storageService.filesUploadAndGetUrls(multipartFiles, isBigFile, directory);
        storageService.deleteFiles(deleteFileUrls);
        return CustomSuccessResponse.ofOk("파일 수정 성공", response);
    }

}
