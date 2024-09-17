package com.chicchoc.sivillage.domain.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Comment("주문 UUID")
    @Column(name = "order_uuid", length = 21, nullable = false)
    private String orderUuid;

    @Comment("사용자 UUID")
    @Column(name = "user_uuid", length = 21, nullable = false)
    private String userUuid;

    @Comment("결제 UUID")
    @Column(name = "payment_uuid", nullable = false)
    private String paymentUuid;

    @Comment("주문 시간")
    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Comment("주문 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 20, nullable = true)
    private OrderStatus orderStatus;

    @Comment("주문자 이름")
    @Column(name = "orderer_name", length = 30, nullable = false)
    private String ordererName;

    @Comment("주문자 이메일")
    @Column(name = "orderer_email", length = 50, nullable = false)
    private String ordererEmail;

    @Comment("주문자 연락처")
    @Column(name = "orderer_phone", length = 20, nullable = false)
    private String ordererPhone;

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

}
