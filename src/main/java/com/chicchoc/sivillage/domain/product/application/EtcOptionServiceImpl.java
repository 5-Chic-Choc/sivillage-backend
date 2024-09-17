package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.EtcOption;
import com.chicchoc.sivillage.domain.product.dto.out.EtcOptionResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.EtcOptionRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EtcOptionServiceImpl implements EtcOptionService {

    private final EtcOptionRepository etcOptionRepository;

    @Override
    public List<EtcOptionResponseDto> getAllEtcOptions() {
        List<EtcOption> etcOptions = etcOptionRepository.findAll();
        return etcOptions.stream()
                .map(EtcOptionResponseDto::fromEntity)
                .toList();
    }

    @Override
    public EtcOptionResponseDto getEtcOptionById(Long id) {
        EtcOption etcOption = etcOptionRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));
        return EtcOptionResponseDto.fromEntity(etcOption);
    }
}