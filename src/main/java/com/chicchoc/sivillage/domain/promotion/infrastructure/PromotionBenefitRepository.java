package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionBenefitRepository extends JpaRepository<PromotionBenefit, Long> {
    List<PromotionBenefit> findByPromotionUuid(String promotionUuid);
}
