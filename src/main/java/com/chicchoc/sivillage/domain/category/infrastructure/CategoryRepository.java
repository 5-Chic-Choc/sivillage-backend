package com.chicchoc.sivillage.domain.category.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // depth가 0인 최상위 카테고리를 조회하는 메서드
    List<Category> findByDepth(int depth);

    // 특정 부모 카테고리의 자식 카테고리를 조회하는 메서드
    List<Category> findByParentId(Long parentId);
}
