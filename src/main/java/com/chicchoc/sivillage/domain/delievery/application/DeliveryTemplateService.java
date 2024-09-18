package com.chicchoc.sivillage.domain.delievery.application;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;

public interface DeliveryTemplateService {
    void createTemplate(String userUuid, DeliveryRequestDto deliveryRequestDto);
}
