package com.chicchoc.sivillage.domain.promotion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class PromotionHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_hashtag_id")
    private Long id;

    @Comment("프로모션 UUID")
    @Column(name = "promotion_uuid", nullable = false, length = 21)
    private String promotionUuid;

    @Comment("해시태그 내용")
    @Column(name = "content", nullable = false, length = 30)
    private String content;
}
