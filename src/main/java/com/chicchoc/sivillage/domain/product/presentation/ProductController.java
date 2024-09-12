package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ProductService;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "getProducts API", description = "상품 목록 조회", tags = {"Product"})
    @GetMapping()
    public BaseResponse<List<ProductResponseVo>> getFilteredProductList(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) Integer minimumPrice,
            @RequestParam(required = false) Integer maximumPrice,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer perPage,
            @RequestParam(defaultValue = "createdAt")
            @Parameter(description = "정렬 기준 (기본값: 'createdAt', 다른 값: 'discount_rate', 'price', 'name')")
            String sortBy,
            @RequestParam(defaultValue = "true") boolean isAscending) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .categories(categories)
                .sizes(sizes)
                .colors(colors)
                .brands(brands)
                .minimumPrice(Optional.ofNullable(minimumPrice).orElse(0))  // 기본값 처리
                .maximumPrice(Optional.ofNullable(maximumPrice).orElse(Integer.MAX_VALUE))  // 기본값 처리
                .page(page)
                .perPage(perPage)
                .sortBy(sortBy)
                .isAscending(isAscending)
                .build();

        List<ProductResponseVo> products = productService.getFilteredProducts(productRequestDto);

        return new BaseResponse<>(products);
    }
}
