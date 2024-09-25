package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductCountAndPageDto;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findFilteredProducts(ProductRequestDto dto);

    ProductCountAndPageDto findFilteredProductsCount(ProductRequestDto dto);

    List<Product> findTopBestProductsByCategory(ProductRequestDto dto);
}
