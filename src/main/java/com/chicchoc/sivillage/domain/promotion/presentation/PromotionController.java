package com.chicchoc.sivillage.domain.promotion.presentation;

import com.chicchoc.sivillage.domain.promotion.application.PromotionService;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionRequestDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionBenefitResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionMediaResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionProductResponseDto;
import com.chicchoc.sivillage.domain.promotion.dto.out.PromotionResponseDto;
import com.chicchoc.sivillage.domain.promotion.vo.in.PromotionRequestVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionBenefitResponseVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionMediaResponseVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionProductResponseVo;
import com.chicchoc.sivillage.domain.promotion.vo.out.PromotionResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public BaseResponse<Void> createPromotion(@RequestBody PromotionRequestVo promotionRequestVo) {
        PromotionRequestDto promotionRequestDto = promotionRequestVo.toDto();

        promotionService.addPromotion(promotionRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "getAllPromotions API", description = "전체 프로모션 조회", tags = {"Promotion"})
    @GetMapping
    public BaseResponse<List<PromotionResponseVo>> getPromotions() {
        List<PromotionResponseDto> promotionResponseDtos = promotionService.findAllPromotions();

        List<PromotionResponseVo> promotionResponseVos = promotionResponseDtos.stream()
                .map(PromotionResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(promotionResponseVos);
    }

    @Operation(summary = "updatePromotion API", description = "프로모션 수정", tags = {"Promotion"})
    @PutMapping({"/{promotionUuid}"})
    public BaseResponse<Void> updatePromotion(
            @PathVariable String promotionUuid,
            @RequestBody PromotionRequestVo promotionRequestVo) {

        PromotionRequestDto promotionRequestDto = promotionRequestVo.toDto();

        promotionService.updatePromotion(promotionUuid, promotionRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "getPromotion API", description = "프로모션 한 건 조회", tags = {"Promotion"})
    @GetMapping("/{promotionUuid}")
    public BaseResponse<PromotionResponseVo> getPromotion(@PathVariable String promotionUuid) {
        PromotionResponseDto promotionResponseDto = promotionService.findPromotion(promotionUuid);

        PromotionResponseVo promotionResponseVo = promotionResponseDto.toResponseVo();

        return new BaseResponse<>(promotionResponseVo);
    }

    @Operation(summary = "getPromotionBenefits API", description = "프로모션 혜택 조회", tags = {"Promotion"})
    @GetMapping("/promotionBenefit/{promotionUuid}")
    public BaseResponse<List<PromotionBenefitResponseVo>> getPromotionBenefits(
            @PathVariable String promotionUuid) {
        List<PromotionBenefitResponseDto> promotionBenefitResponseDtos =
                promotionService.findPromotionBenefits(promotionUuid);

        List<PromotionBenefitResponseVo> promotionBenefitResponseVos =
                promotionBenefitResponseDtos.stream()
                        .map(PromotionBenefitResponseDto::toResponseVo)
                        .toList();

        return new BaseResponse<>(promotionBenefitResponseVos);
    }

    @Operation(summary = "getPromotionMedias API", description = "프로모션 미디어 조회", tags = {"Promotion"})
    @GetMapping("/promotionMedia/{promotionUuid}")
    public BaseResponse<List<PromotionMediaResponseVo>> getPromotionMedias(
            @PathVariable String promotionUuid) {
        List<PromotionMediaResponseDto> promotionMediaResponseDtos =
                promotionService.findPromotionMedias(promotionUuid);

        List<PromotionMediaResponseVo> promotionMediaResponseVos =
                promotionMediaResponseDtos.stream()
                        .map(PromotionMediaResponseDto::toResponseVo)
                        .toList();

        return new BaseResponse<>(promotionMediaResponseVos);
    }

    @Operation(summary = "getFilteredPromotions API", description = "필터링 된 프로모션 목록 조회", tags = {"Promotion"})
    @GetMapping("/filtered")
    public BaseResponse<List<PromotionResponseVo>> getFilteredPromotions(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> benefitTypes,
            @RequestParam(required = false) List<String> brandUuids,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer perPage) {

        PromotionFilterRequestDto promotionFilterRequestDto = PromotionFilterRequestDto.builder()
                .categoryId(categoryId)
                .benefitTypes(benefitTypes)
                .brandUuids(brandUuids)
                .page(page)
                .perPage(perPage)
                .build();

        List<PromotionResponseDto> promotionResponseDtos = promotionService
                .getFilteredPromotions(promotionFilterRequestDto);

        List<PromotionResponseVo> promotionResponseVos = promotionResponseDtos.stream()
                .map(PromotionResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(promotionResponseVos);
    }

    @Operation(summary = "getPromotionProducts API", description = "프로모션 상품 조회", tags = {"Promotion"})
    @GetMapping("/promotionProduct/{promotionUuid}")
    public BaseResponse<List<PromotionProductResponseVo>> getPromotionProducts(
            @PathVariable String promotionUuid) {
        List<PromotionProductResponseDto> promotionProductResponseDtos =
                promotionService.findPromotionProducts(promotionUuid);

        List<PromotionProductResponseVo> promotionProductResponseVos =
                promotionProductResponseDtos.stream()
                        .map(PromotionProductResponseDto::toVo)
                        .toList();

        return new BaseResponse<>(promotionProductResponseVos);
    }

}
