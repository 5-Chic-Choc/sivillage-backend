package com.chicchoc.sivillage.domain.best.application;

import com.chicchoc.sivillage.domain.best.dto.ProductScoreResponseDto;

public interface ProductScoreService {

    void updateProductScores();

    ProductScoreResponseDto getProductScore(String productUuid);
}
