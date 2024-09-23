package com.chicchoc.sivillage.domain.oauth.vo.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OauthUserInfoRequestVo {

    @NotBlank(message = "provider는 필수값입니다.")
    private String oauthProvider;

    @NotBlank(message = "oAuthId는 필수값입니다.")
    private String oauthId;

    @Email(message = "이메일 형식이 아닙니다.")
    private String oauthEmail;
}
