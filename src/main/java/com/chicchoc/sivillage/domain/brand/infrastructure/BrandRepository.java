package com.chicchoc.sivillage.domain.brand.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandUuid(String brandUuid);

    Optional<Brand> findByNameAndBrandListTypeAndNameKo(String name, String brandListType, String nameKo);
}
