package com.chicchoc.sivillage.domain.delievery.application;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.dto.out.DeliveryResponseDto;
import java.util.List;

public interface DeliveryTemplateService {

    void createTemplate(String userUuid, DeliveryRequestDto deliveryRequestDto);

    List<DeliveryResponseDto> getTemplateList(String userUuid);

    DeliveryResponseDto updateTemplate(String templateUuid, DeliveryRequestDto deliveryRequestDto);

    void deleteTemplate(String templateUuid);
}
