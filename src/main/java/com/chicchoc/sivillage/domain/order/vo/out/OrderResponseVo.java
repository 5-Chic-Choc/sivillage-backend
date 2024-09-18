package com.chicchoc.sivillage.domain.order.vo.out;

import com.chicchoc.sivillage.domain.order.domain.DeliveryStatus;
import com.chicchoc.sivillage.domain.order.domain.OrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseVo {
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
}
