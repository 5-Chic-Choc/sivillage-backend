package com.chicchoc.sivillage.domain.category.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameIn(List<String> names);
}
