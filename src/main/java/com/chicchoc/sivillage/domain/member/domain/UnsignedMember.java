package com.chicchoc.sivillage.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Getter
public class UnsignedMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unsigned_member_id")
    private Long id;

    @Comment("회원 이름")
    @Column(nullable = false, length = 255, name = "unsigned_user_uuid")
    private String unsignedUserUUID;

    @Comment("마지막 접속")
    @Column(nullable = true)
    private Date lastConnectedAt;
}
