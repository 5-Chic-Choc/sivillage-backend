package com.chicchoc.sivillage.global.auth.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserTermsRequestDto {

    @NotNull(message = "약관 id는 필수 입력 항목입니다.")
    private Long termsId;

    @NotNull(message = "약관 동의는 필수 입력 항목입니다.")
    private Boolean isAgree;
}
