package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_id")
    private Long id;

    @Comment("상품옵션 uuid")
    @Column(nullable = false, length = 21, name = "product_option_uuid")
    private String productOptionUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Comment("상품 UUID")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("사이즈 ID")
    @Column(nullable = true)
    private Long sizeId;

    @Comment("색상 ID")
    @Column(nullable = true)
    private Long colorId;

    @Comment("기타 옵션 ID")
    @Column(nullable = true)
    private Long etcOptionId;

    @Comment("판매 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @Comment("가격")
    @Column(nullable = false)
    private Integer price;

    @Comment("할인율")
    @Column(nullable = true)
    private Integer discountRate;

    @Comment("할인가격")
    @Column(nullable = true)
    private Integer discountPrice;

    @Builder
    public ProductOption(String productOptionUuid, Product product, String productUuid, Long sizeId, Long colorId,
            Long etcOptionId, SaleStatus saleStatus, Integer price, Integer discountRate, Integer discountPrice) {
        this.productOptionUuid = productOptionUuid;
        this.product = product;
        this.productUuid = productUuid;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.etcOptionId = etcOptionId;
        this.saleStatus = saleStatus;
        this.price = price;
        this.discountRate = discountRate;
        this.discountPrice = discountPrice;
    }
}
