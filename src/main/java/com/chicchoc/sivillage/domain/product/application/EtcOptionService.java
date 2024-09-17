package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.out.EtcOptionResponseDto;

import java.util.List;

public interface EtcOptionService {
    List<EtcOptionResponseDto> getAllEtcOptions();

    EtcOptionResponseDto getEtcOptionById(Long id);
}
