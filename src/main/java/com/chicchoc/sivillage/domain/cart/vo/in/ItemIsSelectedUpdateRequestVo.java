package com.chicchoc.sivillage.domain.cart.vo.in;

import com.chicchoc.sivillage.domain.cart.dto.in.ItemIsSelectedUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ItemIsSelectedUpdateRequestVo {

    private String cartUuid;
    private Boolean isSelected;

    public ItemIsSelectedUpdateRequestDto toDto() {
        return ItemIsSelectedUpdateRequestDto.builder()
                .cartUuid(cartUuid)
                .isSelected(isSelected)
                .build();
    }

}
