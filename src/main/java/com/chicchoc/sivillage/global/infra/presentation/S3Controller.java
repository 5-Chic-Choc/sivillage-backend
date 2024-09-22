package com.chicchoc.sivillage.global.infra.presentation;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.infra.application.S3Service;
import com.chicchoc.sivillage.global.infra.dto.MediaDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/s3")
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping
    public BaseResponse<MediaDto> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {

        MediaDto mediaDto = s3Service.uploadFile(file, "review");
        return new BaseResponse<>(mediaDto);
    }

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
