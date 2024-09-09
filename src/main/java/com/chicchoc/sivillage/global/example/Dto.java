package com.chicchoc.sivillage.global.example;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class Dto {

    @Email
    private String email;

}
