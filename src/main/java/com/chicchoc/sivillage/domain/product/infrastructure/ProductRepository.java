package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {
    List<Product> findAllByBrandId(Long brandId);
}
