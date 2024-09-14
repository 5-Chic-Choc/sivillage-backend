package com.chicchoc.sivillage.domain.oauth.dto.in;

import com.chicchoc.sivillage.domain.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OauthSignUpRequestDto {

    @Email
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private String postalCode;

    private String address;

    private LocalDate birth;

    private String oauthId;

    private String oauthProvider;

    private String oauthEmail;

    public Member toEntity(String encodedPassword, String uuid) {
        return Member.builder()
                .uuid(uuid)
                .email(email)
                .password(encodedPassword)
                .name(name)
                .phone(phone)
                .postalCode(postalCode)
                .address(address)
                .birth(birth)
                .build();
    }
}