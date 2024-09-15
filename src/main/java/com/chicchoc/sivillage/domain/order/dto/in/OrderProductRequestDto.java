package com.chicchoc.sivillage.domain.order.dto.in;

import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    public OrderProduct toEntity(String orderUuid){
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
                .build();
    }
}
