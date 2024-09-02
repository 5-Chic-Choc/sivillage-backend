package com.chicchoc.sivillage.domain.member.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private Long id;

  @Comment("회원 UUID")
  @Column(nullable = false, length = 50)
  private String uuid;

  @Comment("회원 이메일")
  @Column(nullable = false, length = 30, unique = true)
  private String email;

  @Comment("회원 비밀번호")
  @Column(nullable = false, length = 255)
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
    return null;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
