package com.chicchoc.sivillage.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class UnsignedMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unsigned_member_id")
    private Long id;

    private String code;

    private Date lastConnectedAt;
}
