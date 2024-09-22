package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;

    @Comment("제품 옵션 uuid")
    @Column(nullable = false, length = 21, name = "product_option_uuid")
    private String productOptionUuid;

    @Comment("제품 상세 URL")
    @Column(nullable = false, length = 2000)
    private String productDetailUrl;

    @Builder
    public ProductDetail(String productOptionUuid, String productDetailUrl) {
        this.productOptionUuid = productOptionUuid;
        this.productDetailUrl = productDetailUrl;
    }
}
