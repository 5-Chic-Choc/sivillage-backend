package com.chicchoc.sivillage.global.auth.jwt;

import com.chicchoc.sivillage.domain.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    //토큰 생성 메서드 : Duration을 받아 토큰 생성
    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expiredAt.toMillis());
        return makeToken(expiration, member);
    }

    //토큰 생성 메서드 : 만료 시일을 받아 클레임 설정 후 토큰 생성
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                //                .header().type(Header.TYPE).add("typ", "JWT").and()
                .header().type("typ").add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer()) //토큰 발급자
                .issuedAt(now) //토큰 발급 시간
                .expiration(expiry) //토큰 만료 시간
                .subject(member.getEmail()) //토큰 제목
                .claim("uuid", member.getUuid())
                .signWith((SecretKey) getSignKey()) //토큰 서명
                .compact();
    }

    //토큰 유효성 검사
    public boolean validToken(String token) {
        // 검사 중 예외가 발생하면 유효하지 않음 => false 반환
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSignKey()) //토큰 서명 검증
                    .build()
                    .parseSignedClaims(token); //JWT 클레임 파싱
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    //토큰에서 Authentication 객체를 가져오는 메서드
    public Authentication getAuthentication(String token) {

        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")); //권한 설정

        return new UsernamePasswordAuthenticationToken(
                new User(
                        claims.getSubject(),
                        "", authorities),
                token, authorities);
    }

    //토큰에서 사용자의 uuid를 가져오는 메서드
    public String getUserUuid(String token) {
        Claims claims = getClaims(token);
        return claims.get("uuid", String.class);
    }

    //토큰에서 클레임을 가져오는 메서드
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey()) //복호화
                .build()
                .parseSignedClaims(token)//JWT 클레임 파싱
                .getPayload();
    }

    //토큰 서명을 위한 SecretKey 생성
    public Key getSignKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
    }
}
