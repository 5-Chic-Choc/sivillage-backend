package com.chicchoc.sivillage.domain.delievery.presentation;

import com.chicchoc.sivillage.domain.delievery.application.DeliveryTemplateService;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.dto.out.DeliveryResponseDto;
import com.chicchoc.sivillage.domain.delievery.vo.in.DeliveryRequestVo;
import com.chicchoc.sivillage.domain.delievery.vo.out.DeliveryResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveryTemplate")
@RequiredArgsConstructor
public class DeliveryTemplateController {

    private final DeliveryTemplateService deliveryTemplateService;

    @Operation(summary = "createTemplate API", description = "배송 템플릿 생성", tags = {"배송 템플릿"})
    @PostMapping
    public BaseResponse<Void> createTemplate(Authentication authentication,
                                             @RequestBody DeliveryRequestVo deliveryRequestVo) {

        DeliveryRequestDto deliveryRequestDto = deliveryRequestVo.toDto();

        deliveryTemplateService.createTemplate(authentication.getName(), deliveryRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "getTemplate API", description = "배송 템플릿 조회", tags = {"배송 템플릿"})
    @GetMapping
    public BaseResponse<List<DeliveryResponseVo>> getTemplate(Authentication authentication) {

        List<DeliveryResponseDto> deliveryResponseDtoList = deliveryTemplateService.getTemplateList(
                authentication.getName());

        List<DeliveryResponseVo> deliveryResponseVoList = deliveryResponseDtoList.stream()
                .map(DeliveryResponseDto::toVo).toList();

        return new BaseResponse<>(deliveryResponseVoList);
    }

    @Operation(summary = "updateTemplate API", description = "배송 템플릿 수정", tags = {"배송 템플릿"})
    @PutMapping("/{templateUuid}")
    public BaseResponse<DeliveryResponseVo> updateTemplate(@PathVariable("templateUuid") String templateUuid,
                                                           @RequestBody DeliveryRequestVo deliveryRequestVo) {
        DeliveryRequestDto deliveryRequestDto = deliveryRequestVo.toDto();

        deliveryTemplateService.updateTemplate(templateUuid, deliveryRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "deleteTemplate API", description = "배송 템플릿 삭제", tags = {"배송 템플릿"})
    @DeleteMapping("/{templateUuid}")
    public BaseResponse<Void> deleteTemplate(@PathVariable("templateUuid") String templateUuid) {
        deliveryTemplateService.deleteTemplate(templateUuid);
        return new BaseResponse<>();
    }
}
