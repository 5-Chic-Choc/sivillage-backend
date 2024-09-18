package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {


    Optional<ProductLike> findByProductUuidAndUserUuidAndIsLiked(String productUuid, String userUuid, Boolean liked);


}
