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
public class ProductLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_like_id")
    private Long id;

    @Comment("회원 uuid")
    @Column(nullable = false, length = 21, name = "user_uuid")
    private Long userUuid;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private Long productUuid;

    @Comment("좋아요 여부")
    @Column(nullable = false)
    private boolean isLiked;
}
