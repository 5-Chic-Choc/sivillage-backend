package com.chicchoc.sivillage.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    // jwtProperties가 초기화 된 후 secretKey를 생성
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
        }
        return secretKey;
    }

    //토큰 생성 메서드
    public String generateToken(Authentication authentication, long expiredAt) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt);
        Claims claims = Jwts.claims().subject(authentication.getName()).build();

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer()) //토큰 발급자
                .issuedAt(now) //토큰 발급 시간
                .expiration(expiry) //토큰 만료 시간
                .subject(claims.getSubject()) //토큰 제목(uuid)
                .signWith(getSecretKey()) //토큰 서명
                .compact();
    }

    //토큰에서 Authentication 객체를 생성하는 메서드
    public Authentication createAuthentication(String token) {

        Claims claims = parseClaims(token);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>(); //권한 필요시 추후 수정

        return new UsernamePasswordAuthenticationToken(
                new User(
                        claims.getSubject(),
                        "", authorities),
                token, authorities);
    }

    //토큰 유효성 체크 메서드
    public boolean isValidToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean isExpired(String token) {

        return parseClaims(token).getExpiration().before(new Date());
    }

    //토큰에서 사용자의 uuid를 가져오는 메서드
    public String getUserUuid(String token) {

        return parseClaims(token).getSubject();
    }

    // 토큰에서 클레임 파싱하는 메서드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())       // 서명 검증
                    .build()
                    .parseSignedClaims(token)         // JWT 클레임 파싱
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 토큰입니다.");
            throw new IllegalArgumentException("다시 로그인 해주세요.");
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}
