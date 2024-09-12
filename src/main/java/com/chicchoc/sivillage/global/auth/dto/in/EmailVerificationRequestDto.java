package com.chicchoc.sivillage.global.auth.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailVerificationRequestDto {

    @Email
    @NotBlank
    private String email;
}
