package com.chicchoc.sivillage.global.jwt.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequestDto {

    private String refreshToken;

}
