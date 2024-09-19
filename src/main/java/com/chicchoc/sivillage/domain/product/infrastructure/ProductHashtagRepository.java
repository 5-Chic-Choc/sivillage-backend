package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHashtagRepository extends JpaRepository<ProductHashtag, Long> {
    List<ProductHashtag> findByProductUuid(String productUuid);
}
