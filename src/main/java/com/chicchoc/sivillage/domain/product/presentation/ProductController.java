package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ProductService;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.*;
import com.chicchoc.sivillage.domain.product.vo.out.*;
import com.chicchoc.sivillage.domain.search.application.SearchServiceImpl;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final SearchServiceImpl searchServiceImpl;

    @Operation(summary = "getProduct API", description = "상품 단품 조회", tags = {"Product"})
    @GetMapping("/one/{productUuid}")
    public BaseResponse<ProductResponseVo> getProduct(@PathVariable String productUuid) {
        ProductResponseDto productResponseDto = productService.getProduct(productUuid);
        return new BaseResponse<>(productResponseDto.toResponseVo());
    }

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
            @RequestParam(defaultValue = "true") boolean isAscending,
            @RequestParam(required = false) String keywords,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestHeader(value = "X-Unsigned-User-UUID", required = false) String unsignedUserUuid) {

        log.info("UserDetails: {}", userDetails);

        // keywords가 존재하면 Redis에 저장
        if (keywords != null && !keywords.isEmpty()) {
            if (userDetails != null) {
                // 로그인한 경우
                searchServiceImpl.addRecentSearch(userDetails.getUsername(), keywords);
            } else if (unsignedUserUuid != null) {
                // 비로그인 사용자 처리
                searchServiceImpl.addRecentSearch(unsignedUserUuid, keywords);
            }
        }

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .keywords(keywords)
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

        List<ProductResponseDto> products = productService.getFilteredProducts(productRequestDto);

        List<ProductResponseVo> productResponseVos = products.stream()
                .map(ProductResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(productResponseVos);
    }

    @Operation(summary = "getFilteredProductsCount API", description = "필터링 상품 목록 개수 조회", tags = {"Product"})
    @GetMapping("/count")
    public BaseResponse<ProductCountAndPageVo> getFilteredProductCount(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) Integer minimumPrice,
            @RequestParam(required = false) Integer maximumPrice,
            @RequestParam(defaultValue = "20") Integer perPage,
            @RequestParam(required = false) String keywords) {

        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .keywords(keywords)
                .categories(categories)
                .sizes(sizes)
                .colors(colors)
                .brands(brands)
                .minimumPrice(Optional.ofNullable(minimumPrice).orElse(0))  // 기본값 처리
                .maximumPrice(Optional.ofNullable(maximumPrice).orElse(Integer.MAX_VALUE))  // 기본값 처리
                .perPage(perPage)
                .build();

        ProductCountAndPageDto count = productService.getFilteredProductsCount(productRequestDto);

        return new BaseResponse<>(count.toVo());
    }

    @Operation(summary = "getTop100BestProducts API", description = "카테고리별 상위 100개 베스트 상품 조회", tags = {"Product"})
    @GetMapping("/best")
    public BaseResponse<List<ProductResponseVo>> getTop100BestProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "100") Integer perPage) {

        // 카테고리 필터링을 위한 DTO 생성
        ProductRequestDto productRequestDto = ProductRequestDto.builder()
                .categories(categories)
                .page(page)
                .perPage(perPage)
                .build();

        // 상위 100개의 베스트 상품 가져오기
        List<ProductResponseDto> bestProducts = productService.getTopBestProductsByCategory(productRequestDto);

        // DTO를 VO로 변환
        List<ProductResponseVo> productResponseVos = bestProducts.stream()
                .map(ProductResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(productResponseVos);
    }

    @Operation(summary = "getProductOptions API", description = "상품 옵션 조회", tags = {"Product"})
    @GetMapping("/{productUuid}")
    public BaseResponse<List<ProductOptionResponseVo>> getProductOp(@PathVariable String productUuid,
                                                                    @AuthenticationPrincipal UserDetails userDetails,
                                                                    @RequestHeader(value = "X-Unsigned-User-UUID",
                                                                            required = false) String unsignedUserUuid) {

        log.info("UserDetails: {}", userDetails);
        // 로그인 여부에 따른 최근 본 상품 추가
        if (userDetails != null) {
            searchServiceImpl.addRecentViewedProduct(userDetails.getUsername(), productUuid);
        } else if (unsignedUserUuid != null) {
            searchServiceImpl.addRecentViewedProduct(unsignedUserUuid, productUuid);
        }

        List<ProductOptionResponseDto> productOptionResponseDtos = productService.getProductOptions(productUuid);

        List<ProductOptionResponseVo> productOptionResponseVos = productOptionResponseDtos.stream()
                .map(ProductOptionResponseDto::toResponseVo)
                .toList();
        return new BaseResponse<>(productOptionResponseVos);
    }

    @Operation(summary = "getOneProductOption API", description = "상품 단일 옵션 조회", tags = {"Product"})
    @GetMapping("/option/{productOptionUuid}")
    public BaseResponse<ProductOptionResponseVo> getOneProductOption(@PathVariable String productOptionUuid) {

        ProductOptionResponseDto productOptionResponseDto = productService.getOneProductOption(productOptionUuid);

        return new BaseResponse<>(productOptionResponseDto.toResponseVo());
    }

    @Operation(summary = "getProductDetails API", description = "상품 상세 조회", tags = {"Product"})
    @GetMapping("/details/{productOptionUuid}")
    public BaseResponse<List<ProductDetailResponseVo>> getProductDetails(@PathVariable String productOptionUuid) {

        List<ProductDetailResponseDto> productDetailResponseDtos = productService.getProductDetails(productOptionUuid);

        List<ProductDetailResponseVo> productDetailResponseVos = productDetailResponseDtos.stream()
                .map(ProductDetailResponseDto::toVo)
                .toList();

        return new BaseResponse<>(productDetailResponseVos);
    }

    @Operation(summary = "getProductInfos API", description = "상품 정보 조회", tags = {"Product"})
    @GetMapping("/infos/{productUuid}")
    public BaseResponse<List<ProductInfoResponseVo>> getProductInfos(@PathVariable String productUuid) {

        List<ProductInfoResponseDto> productInfoResponseDtos = productService.getProductInfos(productUuid);

        List<ProductInfoResponseVo> productInfoResponseVos = productInfoResponseDtos.stream()
                .map(ProductInfoResponseDto::toVo)
                .toList();

        return new BaseResponse<>(productInfoResponseVos);
    }

    @Operation(summary = "getProductHashtags API", description = "상품 해시태그 조회", tags = {"Product"})
    @GetMapping("/hashtags/{productUuid}")
    public BaseResponse<List<ProductHashtagResponseVo>> getProductHashtags(@PathVariable String productUuid) {

        List<ProductHashtagResponseDto> productHashtagResponseDtos = productService.getProductHashtags(productUuid);

        List<ProductHashtagResponseVo> productHashtagResponseVos = productHashtagResponseDtos.stream()
                .map(ProductHashtagResponseDto::toVo)
                .toList();

        return new BaseResponse<>(productHashtagResponseVos);
    }

    @Operation(summary = "getFilteredProductAttributes API", description = "상품 필터링 속성 조회", tags = {"Product"})
    @GetMapping("/attributes")
    public BaseResponse<FilteredProductAttributesVo> getFilteredProductAttributes(
            @RequestParam(required = false) List<String> categories) {

        ProductRequestDto dto = ProductRequestDto.builder()
                .categories(categories)
                .build();

        FilteredProductAttributesDto attributes = productService.getFilteredProductAttributes(dto);

        return new BaseResponse<>(attributes.toVo());
    }

    @Operation(summary = "getColorSizeMapping API", description = "color별 size 목록 조회", tags = {"Product"})
    @GetMapping("/colors-sizes/{productUuid}")
    public BaseResponse<List<ColorSizeResponseDto>> getColorSizeMapping(@PathVariable String productUuid) {

        Map<ColorResponseDto, List<SizeResponseDto>> colorSizeMap = productService
                .getColorSizeMappingByProductUuid(productUuid);

        List<ColorSizeResponseDto> response = colorSizeMap.entrySet().stream()
                .map(entry -> new ColorSizeResponseDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new BaseResponse<>(response);
    }
}
