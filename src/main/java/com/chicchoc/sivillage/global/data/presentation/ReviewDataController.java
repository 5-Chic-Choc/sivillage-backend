package com.chicchoc.sivillage.global.data.presentation;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.data.application.ReviewDataService;
import com.chicchoc.sivillage.global.data.dto.review.ReviewDataRequestDto;
import com.chicchoc.sivillage.global.data.util.JsonFileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "데이터", description = "데이터 삽입용 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/data")
public class ReviewDataController {

    private final ReviewDataService reviewDataService;

    @Operation(summary = "리뷰 데이터 업로드")
    @PostMapping(value = "/review/all", consumes = {"multipart/form-data"})
    public BaseResponse<Void> uploadReviewData(@RequestParam("file") MultipartFile file) throws IOException {

        List<ReviewDataRequestDto> reviewDataRequestDtos = JsonFileUtil.parseFileToDtoList(
                file,
                ReviewDataRequestDto[].class);

        reviewDataService.saveReviewData(reviewDataRequestDtos);

        return new BaseResponse<>();
    }
}
