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
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_info_id")
    private Long id;

    @Comment("제품 정보 종류")
    @Column(nullable = false, length = 30)
    private String kind;

    @Comment("제품 정보 값")
    @Column(nullable = false, length = 30)
    private String value;

    @Comment("필터링 가능 여부")
    @Column(nullable = false)
    private boolean isFiltered;
}
