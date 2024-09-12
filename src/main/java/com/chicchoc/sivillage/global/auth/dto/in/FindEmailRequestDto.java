package com.chicchoc.sivillage.global.auth.dto.in;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindEmailRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;


    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;

}
