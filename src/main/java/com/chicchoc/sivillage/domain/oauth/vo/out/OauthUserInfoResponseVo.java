package com.chicchoc.sivillage.domain.oauth.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OauthUserInfoResponseVo {

    private String oauthProvider;
    private String oauthId;
    private String oauthEmail;

}
