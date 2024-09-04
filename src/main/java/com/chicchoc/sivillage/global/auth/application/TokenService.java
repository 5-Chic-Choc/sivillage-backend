package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.application.MemberService;
import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import com.chicchoc.sivillage.global.auth.jwt.JwtTokenProvider;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {

        if (!jwtTokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
        String uuid = refreshTokenService.findByRefreshToken(refreshToken).getUuid();
        Member member = memberService.findMemberByUuid(uuid);

        return jwtTokenProvider.generateToken(member, Duration.ofHours(1));
    }

}
