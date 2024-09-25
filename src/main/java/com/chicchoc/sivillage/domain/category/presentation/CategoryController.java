package com.chicchoc.sivillage.domain.category.presentation;

import com.chicchoc.sivillage.domain.category.application.CategoryService;
import com.chicchoc.sivillage.domain.category.dto.out.CategoryResponseDto;
import com.chicchoc.sivillage.domain.category.dto.out.ProductCategoryResponseDto;
import com.chicchoc.sivillage.domain.category.vo.out.CategoryResponseVo;
import com.chicchoc.sivillage.domain.category.vo.out.ProductCategoryResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 조회 API", description = "최상위 카테고리 또는 특정 부모 카테고리의 자식 카테고리를 조회",
            tags = {"Category"})
    @GetMapping
    public BaseResponse<List<CategoryResponseVo>> getCategories(@RequestParam(required = false) Long parentId) {
        List<CategoryResponseDto> categoryResponseDtos = categoryService.getCategories(parentId);

        List<CategoryResponseVo> categoryResponseVos = categoryResponseDtos.stream()
                .map(CategoryResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(categoryResponseVos);
    }

    @Operation(summary = "상품 카테고리 조회 API", description = "상품 카테고리를 조회", tags = {"Category"})
    @GetMapping("/product")
    public BaseResponse<List<ProductCategoryResponseVo>> getProductCategories(
            @RequestParam(required = false) Long productId) {
        List<ProductCategoryResponseDto> categoryResponseDtos = categoryService.getProductCategories(productId);

        List<ProductCategoryResponseVo> productCategoryResponseVos = categoryResponseDtos.stream()
                .map(ProductCategoryResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(productCategoryResponseVos);
    }

    @Operation(summary = "경로를 사용한 카테고리 조회 API", description = "카테고리 경로를 사용하여 카테고리를 조회",
            tags = {"Category"})
    @GetMapping("/path")
    public BaseResponse<CategoryResponseVo> getCategoryByPath(@RequestParam List<String> path) {
        CategoryResponseDto categoryResponseDto = categoryService.getCategoryByPath(path);

        return new BaseResponse<>(categoryResponseDto.toResponseVo());
    }
}