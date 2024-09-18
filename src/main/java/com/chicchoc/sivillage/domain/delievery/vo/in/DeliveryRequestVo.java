package com.chicchoc.sivillage.domain.delievery.vo.in;

import com.chicchoc.sivillage.domain.delievery.dto.in.DeliveryRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeliveryRequestVo {

    private String templateName;
    @JsonProperty("rep")
    private boolean isRep;
    private String postalCode;
    private String roadNameAddress;
    private String lotNumberAddress;
    private String detailAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;

    public DeliveryRequestDto toDto() {
        return DeliveryRequestDto.builder()
                .templateName(templateName)
                .isRep(isRep)
                .postalCode(postalCode)
                .roadNameAddress(roadNameAddress)
                .lotNumberAddress(lotNumberAddress)
                .detailAddress(detailAddress)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();
    }
}
