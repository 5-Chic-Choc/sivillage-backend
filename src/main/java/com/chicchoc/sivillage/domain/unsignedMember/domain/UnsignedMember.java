package com.chicchoc.sivillage.domain.unsignedMember.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "unsignedMember")
public class UnsignedMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unsignedMember_id")
    private Long id;

    @Column(name = "unsignedMember_uuid")
    private String UnsignedMemberUuid;
}
