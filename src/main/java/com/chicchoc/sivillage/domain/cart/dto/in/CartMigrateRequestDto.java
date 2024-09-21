package com.chicchoc.sivillage.domain.cart.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartMigrateRequestDto {

    private String userUuid;
    private String unsignedUserUuid;

    @Builder
    public CartMigrateRequestDto(String userUuid, String unsignedUserUuid) {
        this.userUuid = userUuid;
        this.unsignedUserUuid = unsignedUserUuid;
    }
}
