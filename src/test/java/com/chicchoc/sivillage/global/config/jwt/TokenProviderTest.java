package com.chicchoc.sivillage.global.config.jwt;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import com.chicchoc.sivillage.global.jwt.application.JwtTokenProvider;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TokenProviderTest {
    // 테스트 : 토큰 생성, 유효성 검사, 인증정보 가져오기

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProperties jwtProperties;

    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }

    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {

        // given : 테스트 유저 생성
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";

        Member testUser = memberRepository.save(Member.builder()
                .email(uniqueEmail)
                .password("testpassword")
                .uuid(NanoIdGenerator.generateNanoId())
                .name("TestUser")
                .phone("010-1234-5678")
                .isAutoSignIn(true)
                .build());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                testUser.getUuid(), null, Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        // when : 토큰 생성
        String token = jwtTokenProvider.generateToken(authentication, Duration.ofDays(14).toMillis());

        // then : 토큰이 생성되었는지 확인
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String userUuid = claims.getSubject();

        // 토큰에 담긴 유저 정보가 일치하는지 확인
        assertThat(userUuid).isEqualTo(testUser.getUuid());

    }

    @DisplayName("validToken(): 만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {

        // given : 만료된 토큰 생성
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when : 토큰 유효성 검증
        boolean result = jwtTokenProvider.isValidToken(token);

        // then : 유효성 검증 실패
        assertThat(result).isFalse();
    }


    @DisplayName("validToken(): 유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {

        // given : 유효한 토큰 생성
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when : 토큰 유효성 검증
        boolean result = jwtTokenProvider.isValidToken(token);

        // then : 유효성 검증 성공
        assertThat(result).isTrue();
    }


    @DisplayName("getAuthentication(): 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {

        // given : 토큰 생성
        String userUuid = NanoIdGenerator.generateNanoId();
        String token = JwtFactory.builder()
                .subject(userUuid)
                .build()
                .createToken(jwtProperties);

        // when : 토큰 기반으로 인증정보 가져오기
        Authentication authentication = jwtTokenProvider.createAuthentication(token);

        // then : 인증정보가 일치하는지 확인
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userUuid);
    }

    @DisplayName("getUserUuid(): 토큰으로 유저 UUID를 가져올 수 있다.")
    @Test
    void getUserUuId() {

        // given : 토큰 생성
        String userUuid = NanoIdGenerator.generateNanoId();
        String token = JwtFactory.builder()
                .subject(userUuid)
                .build()
                .createToken(jwtProperties);

        // when : 토큰으로 유저 ID 가져오기
        String uuid = jwtTokenProvider.getUserUuid(token);

        // then :  유저 ID가 일치하는지 확인
        assertThat(uuid).isEqualTo(userUuid);
    }
}