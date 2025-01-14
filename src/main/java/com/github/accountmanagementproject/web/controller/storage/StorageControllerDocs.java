package com.github.accountmanagementproject.web.controller.storage;

import com.github.accountmanagementproject.web.dto.response.CustomSuccessResponse;
import com.github.accountmanagementproject.web.dto.storage.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Azure Blob Storage", description = "AWS 버킷 관련 API (파일 업로드, 취소, 수정)")
public interface StorageControllerDocs {
    @Operation(summary = "파일 한개 또는 여러개 업로드", description = "멀티파트 요청으로 파일 여러개 한번에 업로드 후 <br>업로드된 정보 반환")
    @ApiResponse(responseCode = "201", description = "파일 업로드 성공️ HTTP Status 201 Created",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "파일 업로드 성공 후 반환값 ",
                                    description = "⬆️⬆️ success.responseData 안에 제목과 그리고 첨부된 이미지들을 배열로 목록들을 반환해줍니다. ",
                                    value = """
                                            {
                                              "success": {
                                                "code": 201,
                                                "httpStatus": "OK",
                                                "message": "파일 업로드 성공",
                                                "responseData": [
                                                  {
                                                    "fileName": "perros carinosos (12).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/c637b652-0a9f-4b97-88de-268fac7274ee.jpg"
                                                  },
                                                  {
                                                    "fileName": "perros carinosos (16).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/73dc1b40-d411-4f25-a480-8eb3b169cc7b.jpg"
                                                  },
                                                  {
                                                    "fileName": "perros carinosos (19).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/297ead59-2a9d-4bc7-bb19-4a68343aee3f.jpg"
                                                  }
                                                ],
                                                "timestamp": "2024-10-23T19:44:52.3801181"
                                              }
                                            }""")
                    })
    )
    @ApiResponse(responseCode = "500", description = "서버 업로드 실패",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "서버 에러 발생",
                            description = "⬆️⬆️ 파일 업로드 실패",
                            value = """
                                    {
                                       "error": {
                                         "code": 500,
                                         "httpStatus": "INTERNAL_SERVER_ERROR.",
                                         "request": "perros carinosos (19).jpg",
                                         "systemMessage": null,
                                         "customMessage": "File Upload Failed",
                                         "timestamp": "2024-10-16 15:59:41"
                                       }
                                     }""")
            )
    )
    @ApiResponse(responseCode = "400", description = "요청 실패",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "프론트 요청 실패",
                                    description = "⬆️⬆️ big 쿼리파라미터가 불리언타입이 아니거나 멀티파트파일이 비어있을때 발생됩니다. ",
                                    value = """
                                            {
                                              "timestamp": "2024-10-23T11:11:23.887+00:00",
                                              "status": 400,
                                              "error": "Bad Request",
                                              "path": "/v1/api/storage/multipart-files"
                                            }""")
                    })
    )
    ResponseEntity<CustomSuccessResponse<List<FileDto>>> uploadMultipleFiles(
            @Parameter(
                    description = "multipart/form-data 형식의 첨부파일 리스트를 input으로 받습니다. key 값은 files 입니다<br>"

            )
            List<MultipartFile> multipartFiles,
            @Parameter(description = "파일을 업로드할 디렉토리명"
                    , examples = @ExampleObject(name = "예시", value = "profile_images"))
            String directory,
            @Parameter(description = "첨부파일이 일반파일인지 대용량인지 기본값은 false<br>" +
                    "기본값이 false라 대용량일때만 true 값 넣어주셔도 됩니다 exists) false, true <br>" +
                    "아직 대용량 업로드는 구현 안됐습니다 ㅠ", examples = {
                    @ExampleObject(name = "대용량첨부", value = "true", description = "대용량 파일"),
                    @ExampleObject(name = "일반첨부", value = "false", description = "일반 파일")
            }) boolean isBigFile);

    @Operation(summary = "파일 여러개 삭제", description = "업로드 취소 요청(버킷에서 해당 파일 삭제)")
    @ApiResponse(responseCode = "500", description = "서버 파일 삭제 실패",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "서버 에러 발생",
                            description = "⬆️⬆️ 파일 삭제 실패",
                            value = """
                                    {
                                       "error": {
                                         "code": 500,
                                         "httpStatus": "INTERNAL_SERVER_ERROR.",
                                         "request": "perros carinosos (19).jpg",
                                         "systemMessage": "Indicates that the request was denied due to insufficient permissions.",
                                         "customMessage": "File Delete Failed",
                                         "timestamp": "2024-10-16 15:59:41"
                                       }
                                     }""")
            )
    )
    @ApiResponse(responseCode = "200", description = "파일 여러개 삭제 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "삭제 성공 응답 예",
                                    description = "⬆️⬆️ 성공! (이미 삭제된 파일의 url 또는 존재하지 않는 url로 시도해도 에러는 안뜹니다. 삭제에 실패했을경우에만 에러발생)",
                                    value = """
                                            {
                                              "success": {
                                                "code": 200,
                                                "httpStatus": "OK",
                                                "message": "파일 삭제 성공",
                                                "timestamp": "2024-10-23T19:44:52.3801181"
                                              }
                                            }""")
                    })
    )
    CustomSuccessResponse<Void> deleteMultipleFiles(
            @Parameter(
                    description = "삭제할 파일의 URL들",
                    examples = @ExampleObject(
                            name = "예시", description = "삭제 원하는 파일의 URL을 파라미터로 전달",
                            value = "[\"http://example.com/file1.jpg\", \"http://example.com/file2.jpg\"]"
                    )
            )
            List<String> fileUrls);
    @Operation(summary = "업로드된 파일 수정", description = "업로드된 파일 수정 <br>(삭제할 URL들을 받아서 버킷에서 삭제한후 새로 업로드된 파일은 업로드 후 파일명과 url을 새로 반환)")
    @ApiResponse(responseCode = "200", description = "파일 삭제 후 업로드 성공",
            content = @Content(mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "파일 업로드 성공 후 반환값 예",
                                    description = "⬆️⬆️ data 안에 제목과 그리고 첨부된 이미지들을 files 배열로 목록들을 반환해줍니다. ",
                                    value = """
                                             {
                                              "success": {
                                                "code": 201,
                                                "httpStatus": "OK",
                                                "message": "파일 업로드 성공",
                                                "responseData": [
                                                  {
                                                    "fileName": "perros carinosos (12).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/c637b652-0a9f-4b97-88de-268fac7274ee.jpg"
                                                  },
                                                  {
                                                    "fileName": "perros carinosos (16).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/73dc1b40-d411-4f25-a480-8eb3b169cc7b.jpg"
                                                  },
                                                  {
                                                    "fileName": "perros carinosos (19).jpg",
                                                    "fileUrl": "https://running-crew.s3.ap-northeast-2.amazonaws.com/297ead59-2a9d-4bc7-bb19-4a68343aee3f.jpg"
                                                  }
                                                ],
                                                "timestamp": "2024-10-23T19:44:52.3801181"
                                              }
                                            }""")
                    })
    )
    @ApiResponse(responseCode = "500", description = "서버 업로드 실패",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "서버 에러 발생",
                            description = "⬆️⬆️ 파일 업로드 실패",
                            value = """
                                    {
                                       "error": {
                                         "code": 500,
                                         "httpStatus": "INTERNAL_SERVER_ERROR.",
                                         "request": "perros carinosos (19).jpg",
                                         "systemMessage": null,
                                         "customMessage": "File Upload Failed",
                                         "timestamp": "2024-10-16 15:59:41"
                                       }
                                     }""")
            )
    )
    @ApiResponse(responseCode = "500", description = "서버 파일 삭제 실패",
            content = @Content(mediaType = "application/json",
                    examples =
                    @ExampleObject(name = "서버 에러 발생",
                            description = "⬆️⬆️ 파일 삭제 실패",
                            value = """
                                    {
                                       "error": {
                                         "code": 500,
                                         "httpStatus": "INTERNAL_SERVER_ERROR.",
                                         "request": "perros carinosos (19).jpg",
                                         "systemMessage": "Indicates that the request was denied due to insufficient permissions.",
                                         "customMessage": "File Delete Failed",
                                         "timestamp": "2024-10-16 15:59:41"
                                       }
                                     }""")
            )
    )
    CustomSuccessResponse<List<FileDto>> modifyMultipleFiles(
            @Parameter(
                    description = "삭제할 파일의 URL들",
                    examples = @ExampleObject(
                            name = "예시", description = "삭제 원하는 파일의 URL을 파라미터로 전달",
                            value = "[\"http://example.com/file1.jpg\", \"http://example.com/file2.jpg\"]"
                    )
            )
            List<String> deleteFileUrls,
            @Parameter(
                    description = "multipart/form-data 형식의 첨부파일 리스트를 input으로 받습니다. key 값은 files 입니다"
            )
            List<MultipartFile> multipartFiles,
            @Parameter(description = "파일을 업로드할 디렉토리명"
            , examples = @ExampleObject(name = "예시", value = "blog_images"))
            String directory,
            @Parameter(description = "첨부파일이 일반파일인지 대용량인지 기본값은 false  exists) false, true", examples = {
                    @ExampleObject(name = "일반첨부", value = "false", description = "일반 파일"),
                    @ExampleObject(name = "대용량첨부", value = "true", description = "대용량 파일")
            }) boolean isBigFile);
}
