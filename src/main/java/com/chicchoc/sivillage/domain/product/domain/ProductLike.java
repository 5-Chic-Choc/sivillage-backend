package com.chicchoc.sivillage.domain.product.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
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

    @Comment("상품 uuid")
    @Column(nullable = false, length = 21, name = "product_uuid")
    private String productUuid;

    @Comment("회원 uuid")
    @Column(nullable = false, length = 21, name = "user_uuid")
    private String userUuid;

    @Comment("좋아요 여부")
    @Column(nullable = false)
    private Boolean isLiked;


    public ProductLike(String productUuid, String userUuid, Boolean isLiked) {
        this.productUuid = productUuid;
        this.userUuid = userUuid;
        this.isLiked = isLiked;
    }

    public void update(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
