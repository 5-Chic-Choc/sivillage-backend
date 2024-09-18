package com.chicchoc.sivillage.domain.delievery.vo.in;

import com.chicchoc.sivillage.domain.delievery.dto.in.DelieveryRequestDto;
import lombok.Getter;

@Getter
public class DelieveryRequestVo {

    private String templateName;
    private boolean isRep;
    private String postalCode;
    private String recipientAddress;
    private String recipientName;
    private String recipientPhone;
    private String deliveryName;
    private String deliveryRequest;

    public DelieveryRequestDto toDto(){
        return DelieveryRequestDto.builder()
                .templateName(templateName)
                .isRep(isRep)
                .postalCode(postalCode)
                .recipientAddress(recipientAddress)
                .recipientName(recipientName)
                .recipientPhone(recipientPhone)
                .deliveryName(deliveryName)
                .deliveryRequest(deliveryRequest)
                .build();
    }
}
