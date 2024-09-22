package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHashtagRepository extends JpaRepository<ProductHashtag, Long> {

    List<ProductHashtag> findByProductUuid(String productUuid);

}
