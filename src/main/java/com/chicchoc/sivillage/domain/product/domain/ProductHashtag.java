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
public class ProductHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_hashtag_id")
    private Long id;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("해시태그 내용")
    @Column(nullable = false, length = 30)
    private String hashtagContent;
}
