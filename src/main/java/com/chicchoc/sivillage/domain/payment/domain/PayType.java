package com.chicchoc.sivillage.domain.payment.domain;

import com.chicchoc.sivillage.domain.order.domain.DeliveryStatus;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PayType {

    CASH("현금"),
    CARD("카드"),
    POINT("포인트");

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static PayType fromString(String value) {
        for (PayType payType : PayType.values()) {
            if (payType.type.equals(value)) {
                return payType;
            }
        }
        throw new BaseException(BaseResponseStatus.INVALID_INPUT_VALUE);
    }
}
