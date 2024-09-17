package com.chicchoc.sivillage.domain.category.application;

import com.chicchoc.sivillage.domain.category.dto.out.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getCategories(Long parentId);
}
