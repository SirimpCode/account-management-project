package com.github.accountmanagementproject.web.dto.storage;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class FileDto {
    @NotBlank(message = "파일명은 필수입니다.")
    @Schema(description = "첨부파일 원본 파일명", example = "코딩.jpg")
    private String fileName;

    @NotBlank(message = "URL은 필수입니다.")
    @Schema(description = "첨부파일 URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/c8db15cc-a145-4aa1-869a-e3e650b3fcf9.png")
    private String fileUrl;

}
