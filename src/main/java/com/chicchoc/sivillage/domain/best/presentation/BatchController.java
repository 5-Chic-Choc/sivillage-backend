package com.chicchoc.sivillage.domain.best.presentation;

import com.chicchoc.sivillage.domain.best.application.ProductScoreService;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/batch")
public class BatchController {

    private final ProductScoreService productScoreService;

    @PostMapping("/run")
    @Operation(summary = "runBatch API", description = "배치 실행", tags = {"Batch"})
    public BaseResponse<String> runBatch() {
        productScoreService.updateProductScores();
        return new BaseResponse<>();
    }
}

