package com.chicchoc.sivillage.domain.promotion.application;

import java.util.List;

public interface PromotionLikeService {

    void saveAndDeletePromotionLike(String promotionUuid, String userUuid);

    Boolean isLikedPromotion(String promotionUuid, String userUuid);

    List<String> getLikedPromotionList(String userUuid);

}
