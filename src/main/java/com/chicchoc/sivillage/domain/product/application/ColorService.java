package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.out.ColorResponseDto;

import java.util.List;

public interface ColorService {
    List<ColorResponseDto> getAllColors();

    ColorResponseDto getColorById(Long id);
}