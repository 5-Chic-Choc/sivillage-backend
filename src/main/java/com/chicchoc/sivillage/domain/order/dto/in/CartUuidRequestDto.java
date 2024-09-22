package com.chicchoc.sivillage.domain.order.dto.in;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartUuidRequestDto {

    private String cartUuid;

}
