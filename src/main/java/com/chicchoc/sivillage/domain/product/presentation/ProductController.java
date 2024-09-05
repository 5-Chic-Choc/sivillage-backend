package com.chicchoc.sivillage.domain.product.presentation;


import com.chicchoc.sivillage.domain.product.application.ProductService;
import com.chicchoc.sivillage.domain.product.dto.out.ProductPerBrandResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "getProductByBrand API", description = "브랜드 별 상품 조회", tags = {"Product"})
    @GetMapping("/{brandId}")
    public CommonResponseEntity<List<ProductPerBrandResponseDto>> getProductsByBrandId(@PathVariable Long brandId) {
        log.info("brandId : {}", brandId);

        List<ProductPerBrandResponseDto> productPerBrandResponseDtos = productService.findAllByBrandId(brandId);
        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "상품 조회 성공",
                productPerBrandResponseDtos
        );
    }

    @Operation(summary = "getProducts API", description = "상품 목록 조회", tags = {"Product"})
    @GetMapping("/products")
    public CommonResponseEntity<List<ProductResponseDto>> getFilteredProductList(
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) List<String> color,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) Integer minimumPrice,
            @RequestParam(required = false) Integer maximumPrice,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean isAscending) {

        List<ProductResponseDto> productResponseDtos = productService.getFilteredProducts(
                category,
                size,
                color,
                brand,
                minimumPrice,
                maximumPrice,
                page,
                perPage,
                sortBy,
                isAscending
        );
        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "상품 조회 성공",
                productResponseDtos
        );
    }
}
