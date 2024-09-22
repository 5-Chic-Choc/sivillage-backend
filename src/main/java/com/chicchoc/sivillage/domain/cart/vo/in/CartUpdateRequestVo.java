package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartUpdateRequestVo {

    private String productOptionUuid;
    private Integer amount;

    public CartUpdateRequestDto toDto(String cartUuid) {
        return CartUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .productOptionUuid(productOptionUuid)
                .amount(amount)
                .build();
    }
}
