package com.chicchoc.sivillage.global.auth.presentation;

import com.chicchoc.sivillage.global.auth.application.TokenService;
import com.chicchoc.sivillage.global.auth.dto.CreateAccessTokenRequestDto;
import com.chicchoc.sivillage.global.auth.dto.CreateAccessTokenResponseDto;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh")
    public CommonResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken
            (@RequestBody CreateAccessTokenRequestDto requestDto) {

        String newAccessToken = tokenService.createNewAccessToken(requestDto.getRefreshToken());

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "새로운 액세스 토큰이 발급되었습니다.",
                new CreateAccessTokenResponseDto(newAccessToken)
        );
    }
}

