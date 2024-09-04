package com.chicchoc.sivillage.global.auth.dto;

import com.chicchoc.sivillage.domain.member.domain.Gender;
import com.chicchoc.sivillage.domain.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
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

    private Date birth;

    @NotNull(message = "성별은 필수 입력 항목입니다.")
    private Gender gender;

    @NotNull(message = "자동 로그인 여부는 필수 입력 항목입니다.")
    private boolean isAutoSignIn;

    public Member toEntity(String uuid, String password) {
        return Member.builder()
                .uuid(uuid)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .postalCode(postalCode)
                .address(address)
                .birth(birth)
                .gender(gender)
                .isAutoSignIn(isAutoSignIn)
                .build();
    }

}
