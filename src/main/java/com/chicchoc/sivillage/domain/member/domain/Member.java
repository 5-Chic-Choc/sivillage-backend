package com.chicchoc.sivillage.domain.member.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Member extends BaseEntity implements UserDetails { //사용자 인증 정보 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 50)
    private String uuid;

    @Comment("회원 이메일")
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("회원 비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("회원 이름")
    @Column(nullable = false, length = 30)
    private String name;

    @Comment("회원 전화번호")
    @Column(nullable = false, length = 20)
    private String phone;

    @Comment("회원 우편번호")
    @Column(nullable = true, length = 10)
    private String postalCode;

    @Comment("회원 주소")
    @Column(nullable = true, length = 255)
    private String address;

    @Comment("회원 생년월일")
    @Column(nullable = true)
    private Date birth;

    @Comment("회원 성별")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("회원 자동 로그인 여부")
    @Column(nullable = false)
    private boolean isAutoSignIn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //권한 정책 수립 후 구현
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.uuid; // UUID를 사용자 이름으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        // todo : 계정 만료 확인 로직
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // todo : 계정 잠금 확인 로직
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // todo : 계정 비밀번호 만료 확인 로직
        return true;
    }

    @Override
    public boolean isEnabled() {
        // todo : 계정 사용 가능 여부 확인 로직
        return true;
    }
}
