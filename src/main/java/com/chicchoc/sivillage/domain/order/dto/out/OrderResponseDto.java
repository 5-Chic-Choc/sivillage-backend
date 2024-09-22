package com.chicchoc.sivillage.domain.order.dto.out;

import com.chicchoc.sivillage.domain.order.domain.DeliveryStatus;
import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import com.chicchoc.sivillage.domain.order.domain.OrderStatus;
import com.chicchoc.sivillage.domain.order.vo.out.OrderResponseVo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private String orderUuid;
    private String productUuid;
    private String productName;
    private String brandName;
    private String productPrice;
    private String discountedPrice;
    private String colorValue;
    private String sizeName;
    private String productOption;
    private DeliveryStatus deliveryStatus;
    private int amount;
    private String thumbnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderResponseVo toVo() {
        return OrderResponseVo.builder()
                .orderUuid(orderUuid)
                .productUuid(productUuid)
                .productName(productName)
                .brandName(brandName)
                .productPrice(productPrice)
                .discountedPrice(discountedPrice)
                .colorValue(colorValue)
                .sizeName(sizeName)
                .productOption(productOption)
                .deliveryStatus(deliveryStatus)
                .amount(amount)
                .thumbnailUrl(thumbnailUrl)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static OrderResponseDto fromEntity(OrderProduct orderProduct) {
        return OrderResponseDto.builder()
                .orderUuid(orderProduct.getOrderUuid())
                .productUuid(orderProduct.getProductUuid())
                .productName(orderProduct.getProductName())
                .brandName(orderProduct.getBrandName())
                .productPrice(orderProduct.getProductPrice())
                .discountedPrice(orderProduct.getDiscountedPrice())
                .colorValue(orderProduct.getColorValue())
                .sizeName(orderProduct.getSizeName())
                .productOption(orderProduct.getProductOption())
                .deliveryStatus(orderProduct.getDeliveryStatus())
                .amount(orderProduct.getAmount())
                .thumbnailUrl(orderProduct.getThumbnailUrl())
                .createdAt(orderProduct.getCreatedAt())
                .updatedAt(orderProduct.getUpdatedAt())
                .build();
    }
}
