package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<BrandResponseDto> findAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> BrandResponseDto.builder()
                        .id(brand.getId())
                        .name(brand.getName())
                        .logoUrl(brand.getLogoUrl())
                        .build())
                .toList();
    }
}
