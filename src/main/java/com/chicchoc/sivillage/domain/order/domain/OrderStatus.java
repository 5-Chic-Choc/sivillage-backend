package com.chicchoc.sivillage.domain.order.domain;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PENDING("결제 대기"),
    COMPLETED("결제 완료"),
    CONFIRMED("주문 확인"),
    CANCELLED("주문 취소"),
    FAILED("주문 실패"),
    PREPARING_SHIPMENT("배송 준비 중"),
    ORDER_SHIPPED("배송 완료"),
    ORDER_COMPLETED("구매 확정");

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
