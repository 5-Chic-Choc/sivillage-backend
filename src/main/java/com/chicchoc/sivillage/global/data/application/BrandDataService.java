package com.chicchoc.sivillage.global.data.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.data.dto.brand.BrandDataRequestDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BrandDataService {

    private final BrandRepository brandRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Transactional
    public void saveOrUpdateBrand(List<BrandDataRequestDto> brandDtos) {

        for (BrandDataRequestDto dto : brandDtos) {
            Optional<Brand> existingBrand = brandRepository.findByNameAndBrandListTypeAndNameKo(dto.getBrandName(),
                    dto.getBrandListType(), dto.getBrandNameKo());

            if (existingBrand.isPresent()) {
                // 이미 존재하는 경우 업데이트
                Brand brand = existingBrand.get();
                brand.updateBrand(dto.getBrandNameKo(), null); // logoUrl은 업데이트하지 않음
            } else {
                // 존재하지 않는 경우 새로 생성
                Brand newBrand = Brand.builder()
                        .brandUuid(nanoIdGenerator.generateNanoId()) // UUID 생성
                        .name(dto.getBrandName())
                        .nameKo(dto.getBrandNameKo())
                        .brandListType(dto.getBrandListType())
                        .brandIndexLetter(dto.getBrandIndexLetter())
                        .logoUrl(null) // logoUrl은 넣지 않음(데이터 없음)
                        .build();

                brandRepository.save(newBrand); // DB에 저장
            }
        }

    }


}
