package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.CartUpdateRequestDto;
import lombok.Getter;

@Getter
public class CartUpdateRequestVo {

    private String cartUuid;
    private String cartName;

    public CartUpdateRequestDto toDto(){
        return CartUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .cartName(cartName)
                .build();
    }
}
