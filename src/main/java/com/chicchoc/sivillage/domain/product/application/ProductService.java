package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.out.ProductPerBrandResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductPerBrandResponseDto> findAllByBrandId(Long brandId);

    List<ProductResponseDto> getFilteredProducts(
            List<String> categories,
            List<String> sizes,
            List<String> colors,
            List<String> brands,
            Integer minPrice,
            Integer maxPrice,
            int page,
            int perPage,
            String sortBy,
            boolean isAscending
    );
}
