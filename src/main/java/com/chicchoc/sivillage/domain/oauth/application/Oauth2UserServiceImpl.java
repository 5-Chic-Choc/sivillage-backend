package com.chicchoc.sivillage.domain.oauth.application;

import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.oauth.domain.OauthMember;
import com.chicchoc.sivillage.domain.oauth.infrastructure.OauthMemberRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class Oauth2UserServiceImpl extends DefaultOAuth2UserService {
    // oauth2 사용자 정보를 가져오고 인증 객체 생성

    private final OauthMemberRepository oauthMemberRepository; // Oauth 연동 계정 관리
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // OAuth2User 객체 생성
        OAuth2User oauth2User = super.loadUser(userRequest);

        // OAuth2User 객체가 없거나 속성이 없는 경우 => 예외 처리
        if (oauth2User == null || oauth2User.getAttributes() == null) {
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }

        // OAuth2User 객체에서 제공자, ID, 이메일 추출
        String oauthProvider = userRequest.getClientRegistration().getRegistrationId();
        String oauthMemberId = getOauthMemberId(oauthProvider, oauth2User);
        String oauthMemberEmail = getOauthMemberEmail(oauthProvider, oauth2User);

        // ID 또는 이메일이 없는 경우 => 예외 처리
        if (oauthMemberId == null || oauthMemberEmail == null) {
            throw new BaseException(BaseResponseStatus.INVALID_OAUTH_INFO);
        }

        return oauthMemberRepository.findByOauthIdAndOauthProvider(oauthMemberId, oauthProvider)
                // 연동된 계정이 있는 경우 => 로그인 처리(토큰 발급)
                .map(oauthMember -> {
                    String memberUuid = oauthMember.getMember().getUuid();
                    return new CustomOauth2User(oauthProvider, oauthMemberId, oauthMemberEmail, memberUuid);
                }) // 연동된 계정이 없는 경우 => 회원가입 페이지 이동하도록 fail 반환
                .orElseGet(() -> new CustomOauth2User(oauthProvider, oauthMemberId, oauthMemberEmail, "fail"));

    }

    // 사용자 ID 추출 메서드
    @SuppressWarnings("unchecked")
    private String getOauthMemberId(String oauthProvider, OAuth2User oauth2User) {
        if ("kakao".equals(oauthProvider)) {
            return oauth2User.getAttributes().get("id").toString();
        } else if ("naver".equals(oauthProvider)) {
            Map<String, String> responseMap = (Map<String, String>) oauth2User.getAttributes().get("response");
            return responseMap.get("id");
        }
        return null;
    }

    // 사용자 이메일 추출 메서드
    @SuppressWarnings("unchecked")
    private String getOauthMemberEmail(String oauthProvider, OAuth2User oauth2User) {
        if ("kakao".equals(oauthProvider)) {
            Map<String, String> kakaoAccount = (Map<String, String>) oauth2User.getAttributes().get("kakao_account");
            return kakaoAccount.get("email");
        } else if ("naver".equals(oauthProvider)) {
            Map<String, String> responseMap = (Map<String, String>) oauth2User.getAttributes().get("response");
            return responseMap.get("email");
        }
        return null;
    }
}
