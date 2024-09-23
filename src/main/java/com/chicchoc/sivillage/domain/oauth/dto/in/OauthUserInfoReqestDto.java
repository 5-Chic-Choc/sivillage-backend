package com.chicchoc.sivillage.domain.oauth.dto.in;

import com.chicchoc.sivillage.domain.oauth.dto.out.OauthUserInfoResponseDto;
import com.chicchoc.sivillage.domain.oauth.vo.in.OauthUserInfoRequestVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OauthUserInfoReqestDto {


    private String oauthProvider;
    private String oauthId;
    private String oauthEmail;

    public static OauthUserInfoReqestDto toDto(OauthUserInfoRequestVo vo) {

        return OauthUserInfoReqestDto.builder()
                .oauthProvider(vo.getOauthProvider())
                .oauthId(vo.getOauthId())
                .oauthEmail(vo.getOauthEmail())
                .build();
    }
}
