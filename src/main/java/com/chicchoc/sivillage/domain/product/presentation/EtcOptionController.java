package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.EtcOptionService;
import com.chicchoc.sivillage.domain.product.dto.out.EtcOptionResponseDto;
import com.chicchoc.sivillage.domain.product.vo.out.EtcOptionResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/etcoptions")
public class EtcOptionController {

    private final EtcOptionService etcOptionService;

    @Operation(summary = "getAllEtcOptions API", description = "모든 기타 옵션 조회", tags = {"EtcOption"})
    @GetMapping
    public BaseResponse<List<EtcOptionResponseVo>> getAllEtcOptions() {
        List<EtcOptionResponseDto> etcOptionDtos = etcOptionService.getAllEtcOptions();
        List<EtcOptionResponseVo> etcOptionVos = etcOptionDtos.stream()
                .map(EtcOptionResponseDto::toVo)
                .toList();
        return new BaseResponse<>(etcOptionVos);
    }

    @Operation(summary = "getEtcOptionById API", description = "기타 옵션 상세 조회", tags = {"EtcOption"})
    @GetMapping("/{id}")
    public BaseResponse<EtcOptionResponseVo> getEtcOptionById(@PathVariable Long id) {
        EtcOptionResponseDto etcOptionDto = etcOptionService.getEtcOptionById(id);
        return new BaseResponse<>(etcOptionDto.toVo());
    }
}