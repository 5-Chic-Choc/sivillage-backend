package com.chicchoc.sivillage.domain.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long id;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("대분류")
    @Column(nullable = false, length = 30)
    private String majorCategory;

    @Comment("중분류")
    @Column(nullable = false, length = 30)
    private String middleCategory;

    @Comment("소분류")
    @Column(nullable = false, length = 30)
    private String subCategory;

    @Comment("세부분류")
    @Column(length = 30)
    private String detailCategory;

}
