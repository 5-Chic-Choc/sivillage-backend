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

    @Comment("템플릿 UUID")
    @Column(name = "template_uuid", length = 21, nullable = false)
    private String templateUuid;

    @Comment("배송 템플릿 이름")
    @Column(nullable = false, length = 30)
    private String templateName;

    @Comment("대표 설정")
    @Column(name = "is_rep", nullable = false)
    private boolean isRep;

    @Comment("우편번호")
    @Column(name = "postal_code", length = 10, nullable = false)
    private String postalCode;

    @Comment("배송지")
    @Column(name = "recipient_address", length = 255, nullable = false)
    private String recipientAddress;

    @Comment("수신자 성명")
    @Column(name = "recipient_name", length = 30, nullable = false)
    private String recipientName;

    @Comment("수신자 연략처")
    @Column(name = "recipient_phone", length = 20, nullable = false)
    private String recipientPhone;

    @Comment("배송지 명")
    @Column(name = "delivery_name")
    private String deliveryName;

    @Comment("배송 요청 사항")
    @Column(name = "delivery_request")
    private String deliveryRequest;

    @Comment("사용자 UUID")
    @Column(name = "user_uuid", length = 21, nullable = false)
    private String userUuid;
}
