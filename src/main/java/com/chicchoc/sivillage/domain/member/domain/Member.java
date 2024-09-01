package com.chicchoc.sivillage.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 50)
    private String uuid;

    @Comment("회원 이름")
    @Column(nullable = false, length = 30)
    private String name;

    @Comment("회원 이메일")
    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Comment("회원 비밀번호")
    @Column(nullable = false, length = 30)
    private String password;

    @Comment("회원 전화번호")
    @Column(nullable = false, length = 20)
    private String phone;

    @Comment("회원 우편번호")
    @Column(nullable = false, length = 10)
    private String postalCode;

    @Comment("회원 주소")
    @Column(nullable = true, length = 255)
    private String address;

    @Comment("회원 가입일")
    @Column(nullable = false)
    private Date createdAt;

    @Comment("회원 생년월일")
    @Column(nullable = true)
    private Date birth;

    @Comment("회원 성별")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Comment("회원 자동 로그인 여부")
    @Column(nullable = false)
    private boolean isAutoSignin;
}
