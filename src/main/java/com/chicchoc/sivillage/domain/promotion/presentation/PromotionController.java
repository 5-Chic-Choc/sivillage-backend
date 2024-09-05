package com.chicchoc.sivillage.domain.promotion.presentation;

import com.chicchoc.sivillage.domain.promotion.application.PromotionService;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PromotionController {

    private final PromotionService promotionService;

    @Operation(summary = "getPromotion API", description = "프로모션 조회", tags = {"Promotion"})
    @GetMapping("/main/promotion")
    public CommonResponseEntity<List<PromotionResponseVo>> getPromotions() {
        List<PromotionResponseDto> promotionResponseDtos = promotionService.getPromotions();

        List<PromotionResponseVo> promotionResponseVos = promotionResponseDtos.stream()
                .map(PromotionResponseDto::toResponseVo)
                .toList();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "프로모션 조회 성공",
                promotionResponseVos
        );
    }

}
