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
                        .detailAddress(template.getDetailAddress())
                        .recipientName(template.getRecipientName())
                        .recipientPhone(template.getRecipientPhone())
                        .deliveryName(template.getDeliveryName())
                        .deliveryRequest(template.getDeliveryRequest())
                        .build())
                .toList();
    }

    @Override
    public DeliveryResponseDto updateTemplate(String templateUuid, DeliveryRequestDto deliveryRequestDto) {

        DeliveryTemplate deliveryTemplate = deliveryTemplateRepository.findByTemplateUuid(templateUuid);

        deliveryTemplate = DeliveryTemplate.builder()
                .id(deliveryTemplate.getId())
                .userUuid(deliveryTemplate.getUserUuid())
                .templateUuid(deliveryTemplate.getTemplateUuid())
                .templateName(deliveryRequestDto.getTemplateName())
                .isRep(deliveryRequestDto.isRep())
                .postalCode(deliveryRequestDto.getPostalCode())
                .roadNameAddress(deliveryRequestDto.getRoadNameAddress())
                .lotNumberAddress(deliveryRequestDto.getLotNumberAddress())
                .detailAddress(deliveryTemplate.getDetailAddress())
                .recipientName(deliveryRequestDto.getRecipientName())
                .recipientPhone(deliveryRequestDto.getRecipientPhone())
                .deliveryName(deliveryRequestDto.getDeliveryName())
                .deliveryRequest(deliveryRequestDto.getDeliveryRequest())
                .build();

        deliveryTemplateRepository.save(deliveryTemplate);

        return DeliveryResponseDto.builder()
                .templateUuid(deliveryTemplate.getTemplateUuid())
                .templateName(deliveryTemplate.getTemplateName())
                .isRep(deliveryTemplate.isRep())
                .postalCode(deliveryTemplate.getPostalCode())
                .roadNameAddress(deliveryTemplate.getRoadNameAddress())
                .lotNumberAddress(deliveryTemplate.getLotNumberAddress())
                .detailAddress(deliveryTemplate.getDetailAddress())
                .recipientName(deliveryTemplate.getRecipientName())
                .recipientPhone(deliveryTemplate.getRecipientPhone())
                .deliveryName(deliveryTemplate.getDeliveryName())
                .deliveryRequest(deliveryTemplate.getDeliveryRequest())
                .build();
    }

    @Override
    public void deleteTemplate(String templateUuid) {
        DeliveryTemplate deliveryTemplate = deliveryTemplateRepository.findByTemplateUuid(templateUuid);
        deliveryTemplateRepository.delete(deliveryTemplate);
    }
}
