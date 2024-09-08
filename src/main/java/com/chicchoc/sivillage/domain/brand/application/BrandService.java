package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;

import java.util.List;

public interface BrandService {

    void addBrand(BrandRequestDto brandRequestDto);

    List<BrandResponseDto> findAllBrands();

    void updateBrand(String brandUuid, BrandRequestDto brandRequestDto);
}
