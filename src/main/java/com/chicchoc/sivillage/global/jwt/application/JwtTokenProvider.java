package com.chicchoc.sivillage.global.jwt.application;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
            if(token == null) {
                log.error("토큰이 존재하지 않습니다");
                throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
            }
            log.error("parseClaims token={}", token);
            Claims claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            log.error("claims={}", claims);
            return claims;

        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다");
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 유형의 토큰입니다");
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            log.error("잘못된 토큰입니다");
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.error("SecretKey가 일치하지 않습니다");
            throw new BaseException(BaseResponseStatus.WRONG_JWT_TOKEN);
        }
    }
}
