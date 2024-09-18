package com.chicchoc.sivillage.domain.delievery.presentation;

import com.chicchoc.sivillage.domain.delievery.application.DeliveryTemplateService;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.dto.out.DeliveryResponseDto;
import com.chicchoc.sivillage.domain.delievery.vo.in.DeliveryRequestVo;
import com.chicchoc.sivillage.domain.delievery.vo.out.DeliveryResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deliveryTemplate")
@RequiredArgsConstructor
public class DeliveryTemplateController {

    private final DeliveryTemplateService deliveryTemplateService;

    @PostMapping
    public BaseResponse<Void> createTemplate(Authentication authentication,
            @RequestBody DeliveryRequestVo deliveryRequestVo) {

        DeliveryRequestDto deliveryRequestDto = deliveryRequestVo.toDto();

        deliveryTemplateService.createTemplate(authentication.getName(), deliveryRequestDto);

        return new BaseResponse<>();
    }

    @GetMapping
    public BaseResponse<List<DeliveryResponseVo>> getTemplate(Authentication authentication) {

        List<DeliveryResponseDto> deliveryResponseDtoList = deliveryTemplateService.getTemplateList(
                authentication.getName());

        List<DeliveryResponseVo> deliveryResponseVoList = deliveryResponseDtoList.stream()
                .map(DeliveryResponseDto::toVo).toList();

        return new BaseResponse<>(deliveryResponseVoList);
    }
}
