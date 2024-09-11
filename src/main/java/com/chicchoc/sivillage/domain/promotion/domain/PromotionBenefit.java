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
public class PromotionBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_benefit_id")
    private Long id;

    @Comment("프로모션 UUID")
    @Column(name = "promotion_uuid", nullable = false, length = 21)
    private String promotionUuid;

    @Comment("혜택 내용")
    @Column(name = "benefit_content", nullable = false, length = 30)
    private String benefitContent;
}
