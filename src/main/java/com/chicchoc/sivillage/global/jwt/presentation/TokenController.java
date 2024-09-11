package com.chicchoc.sivillage.global.jwt.presentation;

import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import com.chicchoc.sivillage.global.jwt.application.TokenService;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import com.chicchoc.sivillage.global.jwt.dto.in.CreateAccessTokenRequestDto;
import com.chicchoc.sivillage.global.jwt.dto.out.CreateAccessTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "토큰 관련 API", description = "JWT 토큰 갱신 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class TokenController {

    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "새로운 액세스 토큰 발급", description = "리프레시 토큰을 이용하여 새로운 액세스 토큰을 발급합니다.", tags = {"Token"})
    @PostMapping("/refresh")
    public CommonResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(
            @RequestBody CreateAccessTokenRequestDto requestDto, HttpServletResponse response) {

        String newAccessToken = tokenService.createNewAccessToken(requestDto.getRefreshToken());
        response.setHeader(jwtProperties.getAccessTokenPrefix(), newAccessToken);

        return new CommonResponseEntity<>(
                HttpStatus.CREATED,
                "새로운 액세스 토큰이 발급되었습니다.",
                null
        );
    }
}

