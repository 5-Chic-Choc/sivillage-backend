package com.chicchoc.sivillage.domain.brand.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByNameIn(List<String> names);
}
