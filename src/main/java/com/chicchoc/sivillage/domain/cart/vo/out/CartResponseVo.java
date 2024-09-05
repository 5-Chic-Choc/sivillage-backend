package com.chicchoc.sivillage.domain.cart.vo.out;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseVo {
    private String cartUuid;
}
