package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final NanoIdGenerator nanoIdGenerator;
    private final BrandRepository brandRepository;

    @Override
    public void addBrand(BrandRequestDto brandRequestDto) {

        String brandUuid;

        brandUuid = nanoIdGenerator.generateNanoId();

        brandRepository.save(brandRequestDto.toEntity(brandUuid));
    }

    @Override
    public List<BrandResponseDto> findAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> BrandResponseDto.builder()
                        .brandUuid(brand.getBrandUuid())
                        .name(brand.getName())
                        .logoUrl(brand.getLogoUrl())
                        .build())
                .toList();
    }

    @Override
    public void updateBrand(String brandUuid, BrandRequestDto brandRequestDto) {
        Brand brand = brandRepository.findByBrandUuid(brandUuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 브랜드가 존재하지 않습니다."));

        brand.updateBrand(brandRequestDto.getName(), brandRequestDto.getLogoUrl());
        
        brandRepository.save(brand);
    }
}
