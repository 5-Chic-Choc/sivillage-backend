package com.chicchoc.sivillage.global.auth.dto.in;

import lombok.Getter;

@Getter
public class UserTermsRequestDto {

    private Long termsId;
    private Boolean isAgree;
}
