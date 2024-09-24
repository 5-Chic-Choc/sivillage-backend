package com.chicchoc.sivillage.domain.oauth.dto.in;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .uuid(NanoIdGenerator.generateNanoId())
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .postalCode(postalCode)
                .address(address)
                .birth(birth)
                .build();
    }
}