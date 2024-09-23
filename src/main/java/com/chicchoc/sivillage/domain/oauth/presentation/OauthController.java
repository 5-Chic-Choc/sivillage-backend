package com.chicchoc.sivillage.domain.oauth.presentation;

import com.chicchoc.sivillage.domain.oauth.application.OauthService;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignInRequestDto;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthSignUpRequestDto;
import com.chicchoc.sivillage.domain.oauth.dto.in.OauthUserInfoReqestDto;
import com.chicchoc.sivillage.domain.oauth.dto.out.OauthResponse;
import com.chicchoc.sivillage.domain.oauth.dto.out.OauthUserInfoResponseDto;
import com.chicchoc.sivillage.domain.oauth.vo.in.OauthUserInfoRequestVo;
import com.chicchoc.sivillage.global.auth.dto.out.SignInResponseDto;
import com.chicchoc.sivillage.global.auth.vo.SignInResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Oauth2 관련", description = "소셜로그인/회원가입")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class OauthController {

    private final OauthService oauthService;
    private final JwtProperties jwtProperties;

    @Operation(summary = "회원가입", description = "회원가입 + 로그인(토큰 응답) + 연동")
    @PostMapping("/sign-up")
    public BaseResponse<SignInResponseVo> oauthSignUp(@Valid @RequestBody OauthSignUpRequestDto requestDto,
            HttpServletResponse response) {
        SignInResponseDto signUpResponse = oauthService.oauthSignUp(requestDto);
        return sendTokens(signUpResponse, response);
    }

    @Operation(summary = "로그인 ", description = "로그인(토큰 2종 헤더에 발급) + 연동")
    @PostMapping("/sign-in")
    public BaseResponse<SignInResponseVo> oauthSignIn(@Valid @RequestBody OauthSignInRequestDto requestDto,
            HttpServletResponse response) {
        SignInResponseDto signInResponse = oauthService.oauthSignIn(requestDto);
        return sendTokens(signInResponse, response);
    }

    @Operation(summary = "연동된 계정 확인 API ", description = " @Req: oAuth 정보, @Res: 회원정보 반환 또는 로그인")
    @PostMapping("/user-info")
    public BaseResponse<?> returnOrSignIn(
            @Valid @RequestBody OauthUserInfoRequestVo requestVo, HttpServletResponse response) {

        OauthResponse responseDto = oauthService.returnUserInfoOrSignIn(
                OauthUserInfoReqestDto.toDto(requestVo));

        // 연동된 계정이 없는 경우 => 프론트로 다시 OAuth 정보 전달
        if (responseDto instanceof OauthUserInfoResponseDto) {
            return new BaseResponse<>(BaseResponseStatus.NOT_FOUND_OAUTH_MEMBER,
                    ((OauthUserInfoResponseDto) responseDto).toVo());
        }

        // 나머지는 연동된 계정이 있는 경우
        return sendTokens((SignInResponseDto) responseDto, response);
    }

    private BaseResponse<SignInResponseVo> sendTokens(SignInResponseDto responseDto, HttpServletResponse response) {
        response.setHeader(jwtProperties.getAccessTokenPrefix(), responseDto.getAccessToken());
        response.setHeader(jwtProperties.getRefreshTokenPrefix(), responseDto.getRefreshToken());

        return new BaseResponse<>(responseDto.toVo());
    }
}