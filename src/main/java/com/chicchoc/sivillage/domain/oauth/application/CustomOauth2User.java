package com.chicchoc.sivillage.domain.oauth.application;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;


@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CustomOauth2User implements OAuth2User {

    private String provider;
    private String userId;
    private String email;
    @Getter
    private String memberUuid;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of(
                "provider", provider,
                "userId", userId,
                "email", email,
                "memberUuid", memberUuid
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        // 토큰발급시 claims에 들어갈 이름
        return this.memberUuid;
    }

}
