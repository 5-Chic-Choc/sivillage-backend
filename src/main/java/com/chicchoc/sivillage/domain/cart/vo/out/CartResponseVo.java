package com.chicchoc.sivillage.domain.cart.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseVo {
    private String productOptionUuid;
    private int amount;
    private Boolean isSelected;
}
