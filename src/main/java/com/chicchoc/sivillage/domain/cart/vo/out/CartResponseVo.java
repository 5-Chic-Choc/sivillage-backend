package com.chicchoc.sivillage.domain.cart.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartResponseVo {
    private String productOptionUuid;
    private int amount;
    private Boolean isSelected;
}
