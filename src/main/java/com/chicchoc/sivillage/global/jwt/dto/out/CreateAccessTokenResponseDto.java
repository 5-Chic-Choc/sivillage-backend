package com.chicchoc.sivillage.global.jwt.dto.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateAccessTokenResponseDto {

    private String accessToken;
}
