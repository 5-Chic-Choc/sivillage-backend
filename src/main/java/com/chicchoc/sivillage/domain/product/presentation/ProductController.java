package com.chicchoc.sivillage.domain.product.presentation;


import com.chicchoc.sivillage.domain.product.application.ProductService;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "getProductByBrand API", description = "브랜드 별 상품 조회", tags = {"Product"})
    @GetMapping("/{brandId}")
    public CommonResponseEntity<List<ProductResponseDto>> getProductsByBrandId(@PathVariable Long brandId) {
        log.info("brandId : {}", brandId);

        List<ProductResponseDto> productResponseDtos = productService.findAllByBrandId(brandId);
        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "상품 조회 성공",
                productResponseDtos
        );
    }
}
