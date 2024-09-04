package com.chicchoc.sivillage.global.config.jwt;

import static java.util.Collections.emptyMap;

import com.chicchoc.sivillage.global.auth.jwt.JwtProperties;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtFactory { //토큰 생성 클래스

    // 기본 필드값 설정
    private String subject = "test@email.com";

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
        return Jwts.builder()
                .header().type(Header.TYPE).add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer()) //토큰 발급자
                .issuedAt(issuedAt) //토큰 발급 시간
                .expiration(expiration) //토큰 만료 시간
                .subject(subject) //토큰 제목
                .claim("claims", claims)
                .claim("uuid", claims.get("uuid"))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()),
                        SignatureAlgorithm.HS256) //토큰 서명
                .compact();
    }
}
