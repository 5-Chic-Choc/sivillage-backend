package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    Optional<Product> findByProductUuid(String uuid);

    @Query("SELECT p.productUuid, SUM(op.amount), COUNT(pl), COUNT(r), AVG(r.starPoint) "
            + "FROM Product p "
            + "LEFT JOIN OrderProduct op ON p.productUuid = op.productUuid "
            + "LEFT JOIN ProductLike pl ON p.productUuid = pl.productUuid AND pl.isLiked = true "
            + "LEFT JOIN Review r ON p.productUuid = r.productUuid "
            + "GROUP BY p.productUuid")
    List<Object[]> findProductScores();

}
