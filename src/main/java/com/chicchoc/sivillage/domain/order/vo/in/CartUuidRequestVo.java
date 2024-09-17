package com.chicchoc.sivillage.domain.order.vo.in;

import com.chicchoc.sivillage.domain.order.dto.in.CartUuidRequestDto;
import lombok.Getter;

@Getter
public class CartUuidRequestVo {

    private String cartUuid;

    public CartUuidRequestDto toDto() {
        return CartUuidRequestDto.builder()
                .cartUuid(cartUuid)
                .build();
    }
}
