package com.chicchoc.sivillage.domain.brand.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.BrandLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandLikeRepository extends JpaRepository<BrandLike, Long> {

    Optional<BrandLike> findTopByBrandUuidAndUserUuid(String brandUuid, String userUuid);

    Optional<BrandLike> findTopByBrandUuidAndUserUuidAndIsLiked(String brandUuid, String userUuid, Boolean liked);

    List<BrandLike> findByUserUuidAndIsLiked(String userUuid, Boolean liked);
}
