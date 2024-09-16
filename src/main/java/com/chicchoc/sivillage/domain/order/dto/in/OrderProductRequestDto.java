package com.chicchoc.sivillage.domain.order.dto.in;

import com.chicchoc.sivillage.domain.order.domain.DeliveryStatus;
import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductRequestDto {

    private String productUuid;
    private String productName;
    private String brandName;
    private String productPrice;
    private String discountedPrice;
    private String colorValue;
    private String sizeName;
    private String productOption;
    private int amount;
    private String thumbnailUrl;

    public OrderProduct toEntity(String orderUuid, String deliveryCompany, String trackingNumber,
            DeliveryStatus deliveryStatus) {
        return OrderProduct.builder()
                .orderUuid(orderUuid)
                .productUuid(productUuid)
                .productName(productName)
                .brandName(brandName)
                .productPrice(productPrice)
                .discountedPrice(discountedPrice)
                .colorValue(colorValue)
                .sizeName(sizeName)
                .productOption(productOption)
                .amount(amount)
                .thumbnailUrl(thumbnailUrl)
                .deliveryStatus(deliveryStatus)
                .deliveryCompany(deliveryCompany)
                .trackingNumber(trackingNumber)
                .build();
    }
}
