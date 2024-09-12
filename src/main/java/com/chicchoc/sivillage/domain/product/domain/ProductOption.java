package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Comment("색상ID")
    @Column(nullable = false)
    private Long sizeId;

    @Comment("사이즈ID")
    @Column(nullable = false)
    private Long colorId;

    @Comment("기타옵션ID")
    @Column(nullable = false)
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

}
