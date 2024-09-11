package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.domain.BrandMedia;
import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandMediaResponseDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandMediaRepository;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final NanoIdGenerator nanoIdGenerator;
    private final BrandRepository brandRepository;
    private final BrandMediaRepository brandMediaRepository;

    @Override
    @Transactional
    public void addBrand(BrandRequestDto brandRequestDto) {

        String brandUuid = nanoIdGenerator.generateNanoId();

        if (brandRequestDto.getName().length() > 255) {
            throw new BaseException(BaseResponseStatus.INVALID_BRAND_NAME_LENGTH);
        }

        if (brandRequestDto.getLogoUrl().length() > 2000) {
            throw new BaseException(BaseResponseStatus.INVALID_BRAND_LOGO_URL_LENGTH);
        }

        brandRepository.save(brandRequestDto.toEntity(brandUuid));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponseDto> findAllBrands() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream()
                .map(brand -> BrandResponseDto.builder()
                        .brandUuid(brand.getBrandUuid())
                        .name(brand.getName())
                        .logoUrl(brand.getLogoUrl())
                        .brandListType(brand.getBrandListType())
                        .brandIndexLetter(brand.getBrandIndexLetter())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void updateBrand(String brandUuid, BrandRequestDto brandRequestDto) {
        Brand brand = brandRepository.findByBrandUuid(brandUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_BRAND));

        brand.updateBrand(brandRequestDto.getName(), brandRequestDto.getLogoUrl());

        brandRepository.save(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponseDto findBrandByUuid(String brandUuid) {
        Brand brand = brandRepository.findByBrandUuid(brandUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_BRAND));

        BrandResponseDto brandResponseDto = BrandResponseDto.builder()
                .brandUuid(brand.getBrandUuid())
                .name(brand.getName())
                .logoUrl(brand.getLogoUrl())
                .brandListType(brand.getBrandListType())
                .brandIndexLetter(brand.getBrandIndexLetter())
                .build();

        return brandResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandMediaResponseDto> findAllBrandMedias(String brandUuid) {

        brandRepository.findByBrandUuid(brandUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_BRAND));

        List<BrandMedia> brandMedias = brandMediaRepository.findByBrandUuid(brandUuid);

        return brandMedias.stream()
                .map(brandMedia -> BrandMediaResponseDto.builder()
                        .mediaId(brandMedia.getMediaId())
                        .mediaOrder(brandMedia.getMediaOrder())
                        .build())
                .toList();
    }

}
