package com.chicchoc.sivillage.domain.promotion.presentation;

import com.chicchoc.sivillage.domain.promotion.application.PromotionService;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionHashtagResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.vo.in.PromotionRequestVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionHashtagResponseVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {

    private final PromotionService promotionService;

    @Operation(summary = "createPromotion API", description = "프로모션 생성", tags = {"Promotion"})
    @PostMapping
    public CommonResponseEntity<Void> createPromotion(@RequestBody PromotionRequestVo promotionRequestVo) {
        PromotionRequestDto promotionRequestDto = promotionRequestVo.toDto();

        promotionService.addPromotion(promotionRequestDto);

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "프로모션 생성 성공",
                null
        );
    }

    @Operation(summary = "getAllPromotions API", description = "전체 프로모션 조회", tags = {"Promotion"})
    @GetMapping
    public CommonResponseEntity<List<PromotionResponseVo>> getPromotions() {
        List<PromotionResponseDto> promotionResponseDtos = promotionService.findAllPromotions();

        List<PromotionResponseVo> promotionResponseVos = promotionResponseDtos.stream()
                .map(PromotionResponseDto::toResponseVo)
                .toList();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "프로모션 조회 성공",
                promotionResponseVos
        );
    }

    @Operation(summary = "updatePromotion API", description = "프로모션 수정", tags = {"Promotion"})
    @PutMapping({"/{promotionUuid}"})
    public CommonResponseEntity<Void> updatePromotion(
            @PathVariable String promotionUuid,
            @RequestBody PromotionRequestVo promotionRequestVo) {

        PromotionRequestDto promotionRequestDto = promotionRequestVo.toDto();

        promotionService.updatePromotion(promotionUuid, promotionRequestDto);

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "프로모션 수정 성공",
                null
        );
    }

    @Operation(summary = "getPromotionHashtags API", description = "프로모션 해시태그 조회", tags = {"Promotion"})
    @GetMapping("/promotionHashtag/{promotionUuid}")
    public CommonResponseEntity<List<PromotionHashtagResponseVo>> getPromotionHashtags(
            @PathVariable String promotionUuid) {
        List<PromotionHashtagResponseDto> promotionHashtagResponseDtos =
                promotionService.findPromotionHashtags(promotionUuid);

        List<PromotionHashtagResponseVo> promotionHashtagResponseVos =
                promotionHashtagResponseDtos.stream()
                        .map(PromotionHashtagResponseDto::toResponseVo)
                        .toList();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "프로모션 해시태그 조회 성공",
                promotionHashtagResponseVos
        );
    }

}
