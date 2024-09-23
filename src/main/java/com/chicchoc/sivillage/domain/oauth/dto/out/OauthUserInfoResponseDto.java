package com.chicchoc.sivillage.domain.oauth.dto.out;

import com.chicchoc.sivillage.domain.oauth.dto.in.OauthUserInfoReqestDto;
import com.chicchoc.sivillage.domain.oauth.vo.out.OauthUserInfoResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OauthUserInfoResponseDto implements OauthResponse {

    private String oauthProvider;
    private String oauthId;
    private String oauthEmail;

    public OauthUserInfoResponseVo toVo() {
        return OauthUserInfoResponseVo.builder()
                .oauthProvider(oauthProvider)
                .oauthId(oauthId)
                .oauthEmail(oauthEmail)
                .build();
    }

    public static OauthUserInfoResponseDto of(OauthUserInfoReqestDto requestDto) {
        return OauthUserInfoResponseDto.builder()
                .oauthProvider(requestDto.getOauthProvider())
                .oauthId(requestDto.getOauthId())
                .oauthEmail(requestDto.getOauthEmail())
                .build();
    }
}
