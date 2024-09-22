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
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_media_id")
    private Long id;

    @Comment("상품 옵션 uuid")
    @Column(nullable = false, length = 21, name = "product_option_uuid")
    private String productOptionUuid;

    @Comment("미디어 id")
    @Column(nullable = false, length = 21)
    private Long mediaId;

    @Comment("제품 이미지 순서")
    @Column(nullable = false, name = "product_media_order")
    private int mediaOrder;

    @Builder
    public ProductMedia(String productOptionUuid, Long mediaId, int mediaOrder) {
        this.productOptionUuid = productOptionUuid;
        this.mediaId = mediaId;
        this.mediaOrder = mediaOrder;
    }
}
