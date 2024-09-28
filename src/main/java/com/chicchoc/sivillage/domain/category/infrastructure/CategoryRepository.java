package com.chicchoc.sivillage.domain.category.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // depth가 0인 최상위 카테고리를 조회하는 메서드
    List<Category> findByDepth(int depth);

    // 특정 부모 카테고리의 자식 카테고리를 조회하는 메서드
    List<Category> findByParentId(Long parentId);

    // 부모와 이름으로 카테고리 찾기
    Optional<Category> findByNameAndParent(String name, Category parent);

    // 이름으로 카테고리 찾기
    Optional<Category> findTopByName(String name);

    // 부모 없이 루트 카테고리 찾기
    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.parent IS NULL")
    Optional<Category> findRootCategoryByName(@Param("name") String name);

    Optional<Category> findByName(String name);

    Optional<Category> findByNameAndDepth(String name, int depth);
}
