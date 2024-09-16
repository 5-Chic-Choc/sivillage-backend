package com.chicchoc.sivillage.domain.order.domain;


import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주문 UUID")
    @Column(name = "order_uuid", length = 21, nullable = false)
    private String orderUuid;

    @Comment("제품 UUID")
    @Column(name = "product_uuid", nullable = false)
    private String productUuid;

    @Comment("제품 이름")
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Comment("브랜드 이름")
    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Comment("제품 가격")
    @Column(name = "product_price", nullable = false)
    private String productPrice;

    @Comment("제품 할인 가격")
    @Column(name = "discounted_price", nullable = true)
    private String discountedPrice;

    @Comment("색상 값")
    @Column(name = "color_value", nullable = true)
    private String colorValue;

    @Comment("사이즈 이름")
    @Column(name = "size_name", nullable = true)
    private String sizeName;

    @Comment("제품 옵션")
    @Column(name = "product_option", nullable = true)
    private String productOption;

    @Comment("수량")
    @Column(name = "amount", nullable = false)
    private int amount;

    @Comment("썸네일 URL")
    @Column(name = "thumbnail_url", nullable = true)
    private String thumbnailUrl;

     @Comment("배송 상태")
     @Enumerated(EnumType.STRING)
     @Column(name = "delivery_status", length = 20, nullable = true)
     private DeliveryStatus deliveryStatus;

    @Comment("택배사")
    @Column(name = "delivery_company", length = 20)
    private String deliveryCompany;

    @Comment("운송장 번호")
    @Column(name = "tracking_number", length = 30, nullable = true)
    private String trackingNumber;

}
