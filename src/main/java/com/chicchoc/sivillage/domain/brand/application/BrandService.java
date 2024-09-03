package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;

import java.util.List;

public interface BrandService {

    List<BrandResponseDto> getBrands();
}
