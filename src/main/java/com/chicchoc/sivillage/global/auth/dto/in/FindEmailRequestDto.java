package com.chicchoc.sivillage.global.auth.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FindEmailRequestDto {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;


    @Size(min = 10, max = 11, message = "전화번호는 10자리 또는 11자리여야 합니다.")
    @NotBlank(message = "전화번호는 필수 입력 항목입니다.")
    private String phone;

}
