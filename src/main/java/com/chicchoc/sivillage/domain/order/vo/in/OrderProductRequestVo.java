package com.chicchoc.sivillage.domain.order.vo.in;

import com.chicchoc.sivillage.domain.order.dto.in.OrderProductRequestDto;
import lombok.Getter;

@Getter
public class OrderProductRequestVo {
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

    public OrderProductRequestDto toDto() {
        return OrderProductRequestDto.builder()
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
