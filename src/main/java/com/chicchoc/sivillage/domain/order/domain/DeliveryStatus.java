package com.chicchoc.sivillage.domain.order.domain;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    PREPARING_SHIPMENT("배송 준비 중"),
    INSTRUCTION_ISSUED("배송지시"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료"),
    DELIVERY_FAILED("취소됨");

    private final String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static DeliveryStatus fromString(String value) {
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            if (deliveryStatus.status.equals(value)) {
                return deliveryStatus;
            }
        }
        throw new BaseException(BaseResponseStatus.INVALID_INPUT_VALUE);
    }
}

