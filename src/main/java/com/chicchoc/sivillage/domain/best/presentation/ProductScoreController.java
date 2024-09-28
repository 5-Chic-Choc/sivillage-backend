package com.chicchoc.sivillage.domain.best.presentation;

import com.chicchoc.sivillage.domain.best.application.ProductScoreService;
import com.chicchoc.sivillage.domain.best.vo.out.ProductScoreResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product-scores")
public class ProductScoreController {

    private final ProductScoreService productScoreService;

    @Operation(summary = "getProductScore API", description = "상품 스코어 조회", tags = {"상품 스코어"})
    @GetMapping("/{productUuid}")
    public ProductScoreResponseVo getProductScore(@PathVariable String productUuid) {
        return productScoreService.getProductScore(productUuid).toVo();
    }
}
