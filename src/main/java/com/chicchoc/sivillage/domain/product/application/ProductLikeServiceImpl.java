package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.ProductLike;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

    private final ProductLikeRepository productLikeRepository;

    /**
     * 1. 좋아요가 있는지 확인
     * 2. 좋아요가 있으면 수정(상태 변경)
     * 3. 좋아요가 없으면 저장
     * 강사님 피드백 반영, 데이터 분석을 위해 기존 데이터의 상태를 수정하고 새 값을 넣는 방식을 채택
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAndUpdateProductLike(String productUuid, String userUuid) {
        // 1. 좋아요가 있는지 확인
        ProductLike productLike = productLikeRepository.findByProductUuidAndUserUuidAndIsLiked(productUuid, userUuid, true)
                .orElse(null);

        // 2. 좋아요가 있으면 수정
        if (productLike != null) {
            productLike.update(!productLike.getIsLiked());
        } else {
            // 3. 좋아요가 없으면 저장
            productLikeRepository.save(ProductLike.builder()
                    .productUuid(productUuid)
                    .userUuid(userUuid)
                    .isLiked(true)
                    .build());
        }
    }
}
