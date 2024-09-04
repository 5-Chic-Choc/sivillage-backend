package com.chicchoc.sivillage.global.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequestDto {

    private String refreshToken;

}
