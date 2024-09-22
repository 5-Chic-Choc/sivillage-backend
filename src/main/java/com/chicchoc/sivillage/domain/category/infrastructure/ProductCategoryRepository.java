package com.chicchoc.sivillage.domain.category.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

}
