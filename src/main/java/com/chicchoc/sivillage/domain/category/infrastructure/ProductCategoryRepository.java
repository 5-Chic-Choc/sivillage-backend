package com.chicchoc.sivillage.domain.category.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findByProductId(Long productId);
}
