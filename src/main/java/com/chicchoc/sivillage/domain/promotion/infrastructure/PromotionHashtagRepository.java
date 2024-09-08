package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionHashtagRepository extends JpaRepository<PromotionHashtag, Long> {
}
