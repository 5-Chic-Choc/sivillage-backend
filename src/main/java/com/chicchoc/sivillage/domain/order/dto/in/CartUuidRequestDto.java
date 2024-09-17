package com.chicchoc.sivillage.domain.order.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartUuidRequestDto {
    private String cartUuid;
}
