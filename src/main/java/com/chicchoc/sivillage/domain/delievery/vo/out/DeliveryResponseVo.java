package com.chicchoc.sivillage.domain.delievery.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseVo {
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
}
