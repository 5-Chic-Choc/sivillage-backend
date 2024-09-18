package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.ProductLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, Long> {

    Optional<ProductLike> findTopByProductUuidAndUserUuidAndIsLiked(String productUuid, String userUuid, Boolean liked);

    List<ProductLike> findByUserUuidAndIsLiked(String userUuid, Boolean liked);


}
