package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.dto.out.ColorResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public List<ColorResponseDto> getAllColors() {
        List<Color> colors = colorRepository.findAll();
        return colors.stream()
                .map(ColorResponseDto::fromEntity)
                .toList();
    }

    @Override
    public ColorResponseDto getColorById(Long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_COLOR));
        return ColorResponseDto.fromEntity(color);
    }
}