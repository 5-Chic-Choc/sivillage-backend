package com.chicchoc.sivillage.domain.category.application;

import com.chicchoc.sivillage.domain.category.dto.out.CategoryResponseDto;
import com.chicchoc.sivillage.domain.category.dto.out.ProductCategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getCategories(Long parentId);

    List<ProductCategoryResponseDto> getProductCategories(Long productId);

    CategoryResponseDto getCategoryByPath(List<String> path);
}
