package com.chicchoc.sivillage.domain.product.presentation;


import com.chicchoc.sivillage.domain.product.application.ProductService;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductPerBrandResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.domain.product.vo.out.ProductPerBrandResponseVo;
import com.chicchoc.sivillage.domain.product.vo.out.ProductResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "getProductByBrand API", description = "브랜드 별 상품 조회", tags = {"Product"})
    @GetMapping("/{brandId}")
    public CommonResponseEntity<List<ProductPerBrandResponseVo>> getProductsByBrandId(Long brandId) {

        // 서비스 호출
        List<ProductPerBrandResponseDto> productPerBrandResponseDtos = productService.findAllByBrandId(brandId);

        // DTO를 VO로 변환
        List<ProductPerBrandResponseVo> productPerBrandResponseVos = productPerBrandResponseDtos.stream()
                .map(ProductPerBrandResponseDto::toResponseVo)
                .collect(Collectors.toList());

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "상품 조회 성공",
                productPerBrandResponseVos
        );
    }

    @Operation(summary = "getProducts API", description = "상품 목록 조회", tags = {"Product"})
    @GetMapping()
    public CommonResponseEntity<List<ProductResponseVo>> getFilteredProductList(
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

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .categories(category)
                .sizes(size)
                .colors(color)
                .brands(brand)
                .minimumPrice(minimumPrice)
                .maximumPrice(maximumPrice)
                .page(page)
                .perPage(perPage)
                .sortBy(sortBy)
                .isAscending(isAscending)
                .build();

        // Service 호출
        List<ProductResponseDto> productResponseDtos = productService.getFilteredProducts(
                productRequestDto
        );

        // DTO를 VO로 변환하여 리턴
        List<ProductResponseVo> productResponseVos = productResponseDtos.stream()
                .map(ProductResponseDto::toResponseVo)
                .collect(Collectors.toList());

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "상품 조회 성공",
                productResponseVos
        );
    }


    //    @Operation(summary = "getProductDetail API", description = "상품 상세 조회", tags = {"Product"})
    //    @GetMapping("/detail/{productUuid}")
    //    public CommonResponseEntity<ProductDetailResponseVo> getProductDetail
    //            (@PathVariable ProductDetailRequestVo productDetailRequestVo) {
    //
    //        ProductDetailRequestDto productDetailRequestDto = ProductDetailRequestDto.builder()
    //                .productUuid(productDetailRequestVo.getProductUuid())
    //                .build();
    //
    //        ProductDetailResponseDto productDetailResponseDto = productService
    //        .getProductDetail(productDetailRequestVo);
    //
    //        //        ProductDetailResponseVo productDetailResponseVo = ProductDetailResponseVo.builder()
    //        //                .productUuid(productDetailResponseDto.getProductUuid())
    //        //                .productName(productDetailResponseDto.getProductName())
    //        //                .productDescription(productDetailResponseDto.getProductDescription())
    //        //                .price(productDetailResponseDto.getPrice())
    //        //                .productDetailContent(productDetailResponseDto.getProductDetailContent())
    //        //                .productCode(productDetailResponseDto.getProductCode())
    //        //                .build();
    //
    //        return new CommonResponseEntity<>(
    //                HttpStatus.OK,
    //                "상품 상세 조회 성공",
    //                //                productDetailResponseVo
    //                null
    //        );
    //    }
}
