package com.chicchoc.sivillage.global.auth.dto.out;

import com.chicchoc.sivillage.domain.oauth.dto.out.OauthResponse;
import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto implements OauthResponse {

    private String accessToken;
    private String refreshToken;
    private String uuid;

    public SignInResponseVo toVo() {
        return SignInResponseVo.builder()
                .uuid(this.getUuid())
                .build();
    }
}
