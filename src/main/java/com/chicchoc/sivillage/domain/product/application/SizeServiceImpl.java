package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.dto.out.SizeResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    @Override
    public List<SizeResponseDto> getAllSizes() {
        List<Size> sizes = sizeRepository.findAll();
        return sizes.stream()
                .map(SizeResponseDto::fromEntity)
                .toList();
    }

    @Override
    public SizeResponseDto getSizeById(Long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_SIZE));
        return SizeResponseDto.fromEntity(size);
    }
}