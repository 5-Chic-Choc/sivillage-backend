package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionLikeRepository extends JpaRepository<PromotionLike, Long> {

    Optional<PromotionLike> findTopByPromotionUuidAndUserUuidAndIsLiked(String promotionUuid, String userUuid,
            Boolean liked);

    Optional<PromotionLike> findTopByPromotionUuidAndUserUuid(String promotionUuid, String userUuid);

    List<PromotionLike> findByUserUuidAndIsLiked(String userUuid, Boolean liked);

}
