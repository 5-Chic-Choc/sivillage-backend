package com.chicchoc.sivillage.global.auth.vo;

import com.chicchoc.sivillage.global.auth.dto.in.UserTermsRequestDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class SignUpRequestVo {

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

    private String oauthId;

    private String oauthProvider;

    private String oauthEmail;

    @NotNull(message = "약관 동의는 필수 입력 항목입니다.")
    private List<UserTermsRequestDto> terms;
}
