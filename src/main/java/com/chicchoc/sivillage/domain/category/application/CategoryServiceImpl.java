package com.chicchoc.sivillage.domain.category.application;

import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.domain.ProductCategory;
import com.chicchoc.sivillage.domain.category.dto.out.CategoryResponseDto;
import com.chicchoc.sivillage.domain.category.dto.out.ProductCategoryResponseDto;
import com.chicchoc.sivillage.domain.category.infrastructure.CategoryRepository;
import com.chicchoc.sivillage.domain.category.infrastructure.ProductCategoryRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponseDto> getCategories(Long parentId) {
        List<Category> categories;

        // parentId가 null인 경우 최상위 카테고리를 조회
        if (parentId == null) {
            categories = categoryRepository.findByDepth(0);
        } else {

            // parentId가 존재하는 경우 해당 부모 카테고리가 존재하는지 확인
            categoryRepository.findById(parentId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

            // parentId가 존재하는 경우 해당 부모 카테고리의 자식들을 조회
            categories = categoryRepository.findByParentId(parentId);
        }

        // Category 엔티티를 CategoryResponseDto로 변환
        return categories.stream()
                .map(category -> CategoryResponseDto.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .depth(category.getDepth())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductCategoryResponseDto> getProductCategories(Long productId) {
        List<ProductCategory> productCategories = productCategoryRepository.findByProductId(productId);

        // ProductCategory 엔티티를 ProductCategoryResponseDto로 변환 (fromEntity 사용)
        return productCategories.stream()
                .map(ProductCategoryResponseDto::fromEntity)
                .toList();

    }

    @Transactional(readOnly = true)
    @Override
    public CategoryResponseDto getCategoryByPath(List<String> path) {
        Category category = categoryRepository.findRootCategoryByName(path.get(0))
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));

        for (int i = 1; i < path.size(); i++) {
            category = categoryRepository.findByNameAndParent(path.get(i), category)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_CATEGORY));
        }

        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .depth(category.getDepth())
                .build();
    }
}