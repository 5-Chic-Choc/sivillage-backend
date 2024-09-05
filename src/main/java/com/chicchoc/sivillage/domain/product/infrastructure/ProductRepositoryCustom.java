package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findFilteredProducts(
            List<Long> categories,
            List<Long> sizes,
            List<Long> colors,
            List<Long> brands,
            Integer minPrice,
            Integer maxPrice,
            String sortBy,
            boolean isAscending,
            int page,
            int perPage);
}
