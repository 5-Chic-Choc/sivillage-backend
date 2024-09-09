package com.chicchoc.sivillage.global.auth.dto.out;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckEmailResponseDto {

    private boolean available;
    private String message;

}
