package com.chicchoc.sivillage.domain.promotion.application;

import com.chicchoc.sivillage.domain.promotion.domain.PromotionLike;
import com.chicchoc.sivillage.domain.promotion.infrastructure.PromotionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionLikeServiceImpl implements PromotionLikeService {

    private final PromotionLikeRepository promotionLikeRepository;

    //제품 좋아요와 달리, 이벤트 좋아요는 데이터 활용성이 떨어지므로 좋아요 데이터를 삭제하는 로직으로 결정
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAndDeletePromotionLike(String promotionUuid, String userUuid) {
        //1. 좋아요가 있는지 확인
        PromotionLike promotionLike = promotionLikeRepository.findTopByPromotionUuidAndUserUuid(
                        promotionUuid,
                        userUuid)
                .orElse(null);

        //2. 좋아요가 없으면 좋아요 추가
        if (promotionLike == null) {
            promotionLikeRepository.save(new PromotionLike(promotionUuid, userUuid, true));
        } else {
            //3. 좋아요가 있으면 좋아요 삭제
            promotionLikeRepository.delete(promotionLike);
        }
    }

    // 좋아요 여부 확인
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean isLikedPromotion(String promotionUuid, String userUuid) {

        PromotionLike promotionLike = promotionLikeRepository.findTopByPromotionUuidAndUserUuidAndIsLiked(
                        promotionUuid,
                        userUuid,
                        true)
                .orElse(null);

        return promotionLike != null; // 좋아요가 있으면 true, 없으면 false
    }

    // 좋아요한 이벤트 전체 조회
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> getLikedPromotionList(String userUuid) {

        List<PromotionLike> likedPromotionList = promotionLikeRepository.findByUserUuidAndIsLiked(
                userUuid,
                true);

        Collections.reverse(likedPromotionList);

        // 최근 좋아요한 순으로 정렬
        return likedPromotionList.stream()
                .map(PromotionLike::getPromotionUuid)
                .toList();
    }

}
