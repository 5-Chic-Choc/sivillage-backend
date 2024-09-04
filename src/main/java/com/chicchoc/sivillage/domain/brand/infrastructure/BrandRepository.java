package com.chicchoc.sivillage.domain.brand.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
