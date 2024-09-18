package com.chicchoc.sivillage.domain.delievery.dto.in;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {

    private String templateName;
    private boolean isRep;
    private String postalCode;
    private String roadNameAddress;
    private String lotNumberAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;

    public DeliveryTemplate toEntity(String templateUuid, String userUuid) {
        return DeliveryTemplate.builder()
                .templateUuid(templateUuid)
                .userUuid(userUuid)
                .templateName(templateName)
                .isRep(isRep)
                .postalCode(postalCode)
                .roadNameAddress(roadNameAddress)
                .lotNumberAddress(lotNumberAddress)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();
    }
}
