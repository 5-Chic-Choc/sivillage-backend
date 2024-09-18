package com.chicchoc.sivillage.domain.delievery.dto.out;

import com.chicchoc.sivillage.domain.delievery.vo.out.DeliveryResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {
    private String templateUuid;
    private String templateName;
    private boolean isRep;
    private String postalCode;
    private String roadNameAddress;
    private String lotNumberAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;

    public DeliveryResponseVo toVo() {
        return DeliveryResponseVo.builder()
                .templateUuid(templateUuid)
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
