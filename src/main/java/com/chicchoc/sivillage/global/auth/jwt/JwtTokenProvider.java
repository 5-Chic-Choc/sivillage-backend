package com.chicchoc.sivillage.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider { //JWT 토큰 생성 및 유효성 검증

  private final Environment env;

  public String generateAccessToken(Authentication authentication) {
    Claims claims = Jwts.claims().subject(authentication.getName()).build();
    Date now = new Date();
    Date expiration = new Date(now.getTime() + env.getProperty("jwt.access-expire-time", Long.class).longValue());

    return Jwts.builder()
        //todo : claim 뭐 넣을지
        .signWith(getSignKey())
        .claim("email", claims.getSubject())
        .issuedAt(expiration)
        .compact();
  }

  public Key getSignKey() {
    return Keys.hmacShaKeyFor( env.getProperty("jwt.secret-key").getBytes() );
  }

}
