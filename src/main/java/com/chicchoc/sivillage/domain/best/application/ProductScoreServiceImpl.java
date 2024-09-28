package com.chicchoc.sivillage.domain.best.application;

import com.chicchoc.sivillage.domain.best.domain.ProductScore;
import com.chicchoc.sivillage.domain.best.dto.ProductScoreDto;
import com.chicchoc.sivillage.domain.best.dto.ProductScoreResponseDto;
import com.chicchoc.sivillage.domain.best.infrastructure.ProductScoreRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductScoreServiceImpl implements ProductScoreService {
    private final ProductScoreRepository productScoreRepository;
    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    private static final int BATCH_SIZE = 1000;

    @Transactional
    public void updateProductScores() {

        // 한번에 데이터를 가져오는 JPQL 쿼리 실행
        List<Object[]> productScores = productRepository.findProductScores();

        int count = 0;

        for (Object[] row : productScores) {
            String productUuid = (String) row[0];
            Integer salesCount = (row[1] != null) ? ((Number) row[1]).intValue() : 0;
            Integer likeCount = (row[2] != null) ? ((Number) row[2]).intValue() : 0;
            Integer reviewCount = (row[3] != null) ? ((Number) row[3]).intValue() : 0;
            Float starPointAverage = (row[4] != null) ? ((Number) row[4]).floatValue() : 0.0f;

            // 총 점수 계산
            Float totalScore = calculateTotalScore(salesCount, likeCount, reviewCount, starPointAverage);

            // DTO 생성
            ProductScoreDto productScoreDto = ProductScoreDto.builder()
                    .productUuid(productUuid)
                    .salesCount(salesCount)
                    .likeCount(likeCount)
                    .reviewCount(reviewCount)
                    .starPointAverage(starPointAverage)
                    .totalScore(totalScore)
                    .build();

            // ProductScore 엔티티 업데이트
            ProductScore productScore = productScoreRepository.findByProductUuid(productUuid)
                    .orElse(productScoreDto.toEntity());

            // 엔티티 값 설정 (업데이트)
            productScore.updateScores(salesCount, likeCount, reviewCount, starPointAverage, totalScore);

            // Batch Insert/Update 처리
            entityManager.persist(productScore);

            // 배치 사이즈에 도달하면 flush 및 clear
            if (++count % BATCH_SIZE == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

        // 남은 엔티티들 처리
        entityManager.flush();
        entityManager.clear();
    }

    private Float calculateTotalScore(Integer salesCount,
                                      Integer likeCount,
                                      Integer reviewCount,
                                      Float starPointAverage) {

        // null 값을 방지하기 위해 각 값에 대해 기본값 설정
        salesCount = (salesCount != null) ? salesCount : 0;
        likeCount = (likeCount != null) ? likeCount : 0;
        reviewCount = (reviewCount != null) ? reviewCount : 0;
        starPointAverage = (starPointAverage != null) ? starPointAverage : 0.0f;

        return (0.5f * salesCount) + (0.3f * likeCount) + (0.2f * reviewCount) + starPointAverage;
    }

    @Override
    public ProductScoreResponseDto getProductScore(String productUuid) {

        ProductScore productScore = productScoreRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_SCORE));

        return ProductScoreResponseDto.fromEntity(productScore);
    }
}
