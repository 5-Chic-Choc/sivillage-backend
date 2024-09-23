package com.chicchoc.sivillage.domain.oauth.dto.in;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.oauth.domain.OauthMember;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OauthSignInRequestDto {

    @Email
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotBlank
    private String oauthId;

    @NotBlank
    private String oauthProvider;

    private String oauthEmail;

    @Builder
    public OauthSignInRequestDto(String email, String password, String oauthId, String oauthProvider,
            String oauthEmail) {
        this.email = email;
        this.password = password;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.oauthEmail = oauthEmail;
    }

    public OauthMember toEntity(Member member) {
        return OauthMember.builder()
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .oauthEmail(oauthEmail)
                .member(member)
                .build();
    }
}