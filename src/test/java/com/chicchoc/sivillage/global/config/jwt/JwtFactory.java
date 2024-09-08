package com.chicchoc.sivillage.global.config.jwt;

import static java.util.Collections.emptyMap;

import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtFactory { //토큰 생성 클래스

    // 기본 필드값 설정
    private SecretKey secretKey;

    private String subject = new NanoIdGenerator().generateNanoId();

    private Date issuedAt = new Date();

    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());

    private Map<String, Object> claims = emptyMap();

    // 필수 생성자
    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration,
            Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    // 기본값 설정
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    // 토큰 생성 메서드
    public String createToken(JwtProperties jwtProperties) {

        secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer()) //토큰 발급자
                .issuedAt(issuedAt) //토큰 발급 시간
                .expiration(expiration) //토큰 만료 시간
                .subject(subject)
                .claim("testClaims", claims.get("testClaims"))
                .signWith(secretKey) //토큰 서명
                .compact();
    }
}
