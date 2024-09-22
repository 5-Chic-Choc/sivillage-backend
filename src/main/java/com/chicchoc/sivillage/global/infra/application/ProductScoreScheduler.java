package com.chicchoc.sivillage.global.infra.application;

import com.chicchoc.sivillage.domain.best.application.ProductScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScoreScheduler {

    private final ProductScoreService productScoreService;

    // 3 AM 마다 배치 Run
    @Scheduled(cron = "0 0 3 * * ?")
    public void updateProductScoresDaily() {
        productScoreService.updateProductScores();
    }
}
