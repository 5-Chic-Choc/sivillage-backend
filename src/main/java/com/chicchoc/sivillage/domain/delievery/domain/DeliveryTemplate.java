package com.chicchoc.sivillage.domain.delievery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class DeliveryTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_template_id")
    private Long id;

    @Comment("배송 템플릿 이름")
    @Column(nullable = false, length = 30)
    private String name;

    @Comment("대표 설정")
    @Column(nullable = false)
    private boolean isIssue;

    @Comment("우편번호")
    @Column(nullable = false, length = 10)
    private String postCode;

    @Comment("배송지")
    @Column(nullable = false, length = 255)
    private String recipientAddress;

    @Comment("수신자 성명")
    @Column(nullable = false, length = 30)
    private String recipientName;

    @Comment("수신자 연락처")
    @Column(nullable = false, length = 20)
    private String recipientPhone;

    @Comment("배송 요청사항")
    @Column(nullable = false, length = 200)
    private String deliveryMemo;

    @Comment("회원 ID")
    @Column(nullable = false)
    private Long userId;
}
