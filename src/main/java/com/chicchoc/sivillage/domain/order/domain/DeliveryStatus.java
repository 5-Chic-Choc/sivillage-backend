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

    ACCEPT("결제완료"),
    INSTRUCT("상품준비중"),
    DEPARTURE("배송지시"),
    DELIVERING("배송중"),
    FINAL_DELIVERY("배송완료"),
    CANCELED("취소됨"),
    NONE_TRACKING("업체 직접 배송/ 추적불가");

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

