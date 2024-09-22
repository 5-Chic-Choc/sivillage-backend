package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProductStatusUpdateRequestDto {

    private String cartUuid;
    private String cartProductUuid;
    private int quantity;
    private Boolean isSelected;

}
