package com.chicchoc.sivillage.global.auth.dto;

import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private String uuid;

    public SignInResponseVo toVo(SignInResponseDto signInResponseDto) {
        return SignInResponseVo.builder()
                .uuid(signInResponseDto.getUuid())
                .build();
    }
}
