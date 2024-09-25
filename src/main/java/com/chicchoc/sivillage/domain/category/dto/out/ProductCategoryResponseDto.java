package com.chicchoc.sivillage.domain.category.dto.out;

import com.chicchoc.sivillage.domain.category.domain.ProductCategory;
import com.chicchoc.sivillage.domain.category.vo.out.ProductCategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCategoryResponseDto {
    private Long id;
    private Long categoryId;
    private Long productId;

    public ProductCategoryResponseVo toResponseVo() {
        return ProductCategoryResponseVo.builder()
                .id(this.id)
                .productId(this.productId)
                .categoryId(this.categoryId)
                .build();
    }

    public static ProductCategoryResponseDto fromEntity(ProductCategory productCategory) {
        return ProductCategoryResponseDto.builder()
                .id(productCategory.getId())
                .productId(productCategory.getProductId())
                .categoryId(productCategory.getCategoryId())
                .build();
    }
}
