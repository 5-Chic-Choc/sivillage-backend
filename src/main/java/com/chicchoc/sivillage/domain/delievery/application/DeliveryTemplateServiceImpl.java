package com.chicchoc.sivillage.domain.delievery.application;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.chicchoc.sivillage.domain.delievery.dto.out.DeliveryResponseDto;
import com.chicchoc.sivillage.domain.delievery.infrastructure.DeliveryTemplateRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.util.List;
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

    @Override
    public List<DeliveryResponseDto> getTemplateList(String userUuid) {
        List<DeliveryTemplate> templateList = deliveryTemplateRepository.findByUserUuid(userUuid);

        return templateList.stream()
                .map(template -> DeliveryResponseDto.builder()
                        .templateUuid(template.getTemplateUuid())
                        .templateName(template.getTemplateName())
                        .isRep(template.isRep())
                        .postalCode(template.getPostalCode())
                        .roadNameAddress(template.getRoadNameAddress())
                        .lotNumberAddress(template.getLotNumberAddress())
                        .recipientName(template.getRecipientName())
                        .recipientPhone(template.getRecipientPhone())
                        .deliveryName(template.getDeliveryName())
                        .deliveryRequest(template.getDeliveryRequest())
                        .build())
                .toList();
    }
}
