package com.chicchoc.sivillage.global.auth.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.chicchoc.sivillage.domain.member.domain.Gender;
import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.global.auth.domain.RefreshToken;
import com.chicchoc.sivillage.global.auth.dto.CreateAccessTokenRequestDto;
import com.chicchoc.sivillage.global.auth.infrastructure.RefreshTokenRepository;
import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import com.chicchoc.sivillage.global.config.jwt.JwtFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RequiredArgsConstructor
@SpringBootTest
@AutoConfigureMockMvc
class TokenControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        memberRepository.deleteAll();
    }

    @DisplayName("createNewAccessToken : 새로운 액세스 토큰 발급")
    @Test
    public void crateNewAccessToken() throws Exception {

        //given

        final String url = "/api/v1/auth/refresh";
        Random random = new Random();
        int randomNumber = 100 + random.nextInt(900); // 100부터 999까지의 난수 생성
        String uniqueEmail = "testuser" + randomNumber + "@gmail.com";

        // 테스트를 위한 멤버 생성
        Member testMember = memberRepository.save(Member.builder()
                .email(uniqueEmail)
                .password("test")
                .uuid(UUID.randomUUID().toString().substring(0, 5))
                .name("zz")
                .phone("010-1234-5678")
                .gender(Gender.GENDER_MALE)
                .isAutoSignIn(true)
                .build());

        // 리프레시 토큰 생성
        String refreshToken = JwtFactory.builder()
                .claims(Map.of("uuid", testMember.getUuid()))
                .build()
                .createToken(jwtProperties);

        // 리프레시 토큰 저장
        refreshTokenRepository.save(new RefreshToken(testMember.getUuid(), refreshToken));

        // 리프레시 토큰을 요청 바디에 담아서 요청
        CreateAccessTokenRequestDto requestDto = new CreateAccessTokenRequestDto();
        requestDto.setRefreshToken(refreshToken);

        final String requestBody = objectMapper.writeValueAsString(requestDto);

        //when : 새로운 액세스 토큰 발급 요청
        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then : 새로운 액세스 토큰 발급 성공 검증
        resultActions
                //                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty());
    }
}