package com.chicchoc.sivillage.domain.product.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_like_id")
    private Long id;

    @Comment("회원 uuid")
    @Column(nullable = false, length = 21, name = "user_uuid")
    private String userUuid;

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("좋아요 여부")
    @Column(nullable = false)
    private Boolean isLiked;



    @Builder
    public ProductLike(String userUuid, String productUuid, boolean isLiked) {
        this.userUuid = userUuid;
        this.productUuid = productUuid;
        this.isLiked = isLiked;
    }

    public void update(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
