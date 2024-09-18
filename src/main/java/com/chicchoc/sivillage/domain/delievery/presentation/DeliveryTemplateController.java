package com.chicchoc.sivillage.domain.delievery.presentation;

import com.chicchoc.sivillage.domain.delievery.application.DeliveryTemplateService;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.vo.in.DeliveryRequestVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
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
}
