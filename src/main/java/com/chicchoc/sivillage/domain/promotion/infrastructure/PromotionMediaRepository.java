package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionMediaRepository extends JpaRepository<PromotionMedia, Long> {
    List<PromotionMedia> findByPromotionUuid(String promotionUuid);
}
