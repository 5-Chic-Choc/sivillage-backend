package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.ItemQuantityUpdateRequestDto;
import lombok.Getter;

@Getter
public class ItemQuantityUpdateRequestVo {

    private String cartUuid;
    private int quantity;

    public ItemQuantityUpdateRequestDto toDto() {
        return ItemQuantityUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .quantity(quantity)
                .build();
    }
}
