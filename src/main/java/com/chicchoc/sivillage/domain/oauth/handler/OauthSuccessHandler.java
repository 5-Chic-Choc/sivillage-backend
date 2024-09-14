package com.chicchoc.sivillage.domain.oauth.handler;

import com.chicchoc.sivillage.domain.oauth.application.CustomOauth2User;
import com.chicchoc.sivillage.domain.oauth.application.Oauth2UserServiceImpl;
import com.chicchoc.sivillage.domain.oauth.infrastructure.OauthMemberRepository;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OauthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { // 로그인 성공 핸들러

    private final Oauth2UserServiceImpl oauth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final OauthMemberRepository oauthMemberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // OAuth2User 객체 생성 맟 연동된 계정 정보 추출
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
        String oauthMemberId = oauth2User.getAttribute("userId");
        String provider = oauth2User.getAttribute("provider");
        String email = oauth2User.getAttribute("email");
        String memberUuid = oauth2User.getMemberUuid();

        // 연동된 계정이 없을 경우, 프론트엔드로 OAuth 정보 전달 및 리다이렉트
        if (memberUuid.equals("fail")) {
            log.info("연동된 계정이 없으므로 프론트로 OAuth 정보 전달.");
            String redirectUrl = "http://localhost:3000/oauth-link";
            response.sendRedirect(
                    redirectUrl + "?oauthId=" + oauthMemberId + "&provider=" + provider + "&email=" + email);
            return;
        }

        // 연동된 계정이 있을 경우, JWT 토큰 발급 및 프론트엔드로 전달
        String accessToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getAccessExpireTime());
        String refreshToken = jwtTokenProvider.generateToken(authentication, jwtProperties.getRefreshExpireTime());

        response.sendRedirect(
                "http://localhost:3000/oauth?accessToken=" + accessToken + "&refreshToken=" + refreshToken);
    }

}
