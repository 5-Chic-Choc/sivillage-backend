package com.chicchoc.sivillage.global.auth.dto.in;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.oauth.domain.OauthMember;
import com.chicchoc.sivillage.global.auth.vo.SignUpRequestVo;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@ToString
public class SignUpRequestDto {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;

    private String postalCode;

    private String address;

    private LocalDate birth;

    @NotNull(message = "약관 동의는 필수 입력 항목입니다.")
    private List<UserTermsRequestDto> terms;

    private String oauthId;

    private String oauthProvider;

    private String oauthEmail;

    // Vo -> Dto
    public static SignUpRequestDto toDto(SignUpRequestVo signUpRequestVo) {
        return SignUpRequestDto.builder()
                .email(signUpRequestVo.getEmail())
                .password(signUpRequestVo.getPassword())
                .name(signUpRequestVo.getName())
                .phone(signUpRequestVo.getPhone())
                .postalCode(signUpRequestVo.getPostalCode())
                .address(signUpRequestVo.getAddress())
                .birth(signUpRequestVo.getBirth())
                .oauthId(signUpRequestVo.getOauthId())
                .oauthProvider(signUpRequestVo.getOauthProvider())
                .oauthEmail(signUpRequestVo.getOauthEmail())
                .terms(signUpRequestVo.getTerms())
                .build();
    }

    // Dto -> Entity
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

    // Dto -> OauthEntity
    public OauthMember toOauthEntity(Member member) {
        return OauthMember.builder()
                .oauthId(oauthId)
                .oauthProvider(oauthProvider)
                .oauthEmail(oauthEmail)
                .member(member)
                .build();
    }
}


