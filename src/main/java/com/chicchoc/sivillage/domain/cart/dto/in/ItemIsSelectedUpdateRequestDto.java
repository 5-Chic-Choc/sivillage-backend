package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemIsSelectedUpdateRequestDto {
    private String cartUuid;
    private Boolean isSelected;

    @Builder
    public ItemIsSelectedUpdateRequestDto(String cartUuid, Boolean isSelected) {
        this.cartUuid = cartUuid;
        this.isSelected = isSelected;
    }
}
