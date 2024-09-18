package com.chicchoc.sivillage.domain.delievery.application;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.infrastructure.DeliveryTemplateRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryTemplateServiceImpl implements DeliveryTemplateService {

    private final DeliveryTemplateRepository deliveryTemplateRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public void createTemplate(String userUuid, DeliveryRequestDto deliveryRequestDto) {
        String templateUuid = nanoIdGenerator.generateNanoId();
        deliveryTemplateRepository.save(deliveryRequestDto.toEntity(templateUuid, userUuid));
    }
}
