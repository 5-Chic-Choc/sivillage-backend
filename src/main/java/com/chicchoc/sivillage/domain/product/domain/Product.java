package com.chicchoc.sivillage.domain.product.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid", unique = true)
    private String productUuid;

    @Comment("브랜드 uuid")
    @Column(nullable = false, length = 21, name = "brand_uuid")
    private String brandUuid;

    @Comment("제품 이름")
    @Column(nullable = false, length = 100, name = "product_name")
    private String productName;

    @Builder
    public Product(String productUuid, String brandUuid, String productName) {
        this.productUuid = productUuid;
        this.brandUuid = brandUuid;
        this.productName = productName;
    }
}
