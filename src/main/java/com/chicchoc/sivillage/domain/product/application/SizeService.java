package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.out.SizeResponseDto;

import java.util.List;

public interface SizeService {
    List<SizeResponseDto> getAllSizes();

    SizeResponseDto getSizeById(Long id);
}