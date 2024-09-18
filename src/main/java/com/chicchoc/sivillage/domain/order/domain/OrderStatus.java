package com.chicchoc.sivillage.domain.order.domain;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jdk.jshell.Snippet.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    CREATED("결제완료"),
    WAITING("승인대기중"),
    PROGRESS("배송중"),
    COMPLETE("배송완료"),
    BUY_DECISION("구매확정"),
    CANCELED("취소됨");

    private final String status;

    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static OrderStatus fromString(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.status.equals(value)) {
                return orderStatus;
            }
        }
        throw new BaseException(BaseResponseStatus.INVALID_INPUT_VALUE);
    }
}
