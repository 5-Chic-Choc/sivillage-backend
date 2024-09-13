package com.chicchoc.sivillage.domain.product.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
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
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("브랜드 uuid")
    @Column(nullable = false, length = 21, name = "brand_uuid")
    private String brandUuid;

    @Comment("제품 이름")
    @Column(nullable = false, length = 30)
    private String productName;
}
