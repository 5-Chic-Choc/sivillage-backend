package com.chicchoc.sivillage.global.infra.presentation;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.infra.application.S3Service;
import com.chicchoc.sivillage.global.infra.dto.MediaDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @Operation(summary = "uploadFile API", description = "파일 업로드", tags = {"S3"})
    @PostMapping
    public BaseResponse<MediaDto> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {

        MediaDto mediaDto = s3Service.uploadFile(file, "review");
        return new BaseResponse<>(mediaDto);
    }

    @Operation(summary = "getFile API", description = "파일 조회", tags = {"S3"})
    @GetMapping
    public BaseResponse<Void> getFile(String category, String fileName) throws IOException {
        // 테스트용
        category = "review";
        // 테스트용
        fileName = "FLcGUnDoUDm_PHulV_a1b.jpg";

        // String imageUrl = s3Service.getFile(category, fileName);
        return new BaseResponse<>();
    }
}
