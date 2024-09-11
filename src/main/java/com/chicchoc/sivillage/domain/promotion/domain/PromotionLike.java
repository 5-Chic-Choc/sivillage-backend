package com.chicchoc.sivillage.domain.promotion.domain;

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
public class PromotionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_like_id")
    private Long id;

    @Comment("유저 UUID")
    @Column(nullable = false, length = 21)
    private String userUuid;

    @Comment("프로모션 UUID")
    @Column(nullable = false, length = 21)
    private String promotionUuid;

    @Comment("좋아요 여부")
    @Column(nullable = false)
    private boolean isLiked;
}
