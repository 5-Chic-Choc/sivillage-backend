package com.chicchoc.sivillage.domain.brand.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.BrandMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandMediaRepository extends JpaRepository<BrandMedia, Long> {
    List<BrandMedia> findByBrandUuid(String brandUuid);
}
