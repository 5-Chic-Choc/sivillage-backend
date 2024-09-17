package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.SizeService;
import com.chicchoc.sivillage.domain.product.dto.out.SizeResponseDto;
import com.chicchoc.sivillage.domain.product.vo.out.SizeResponseVo;
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
@RequestMapping("/api/v1/sizes")
public class SizeController {

    private final SizeService sizeService;

    @Operation(summary = "getAllSizes API", description = "사이즈 전체 조회", tags = {"Size"})
    @GetMapping
    public BaseResponse<List<SizeResponseVo>> getAllSizes() {
        List<SizeResponseDto> sizeDtos = sizeService.getAllSizes();
        List<SizeResponseVo> sizeVos = sizeDtos.stream()
                .map(SizeResponseDto::toVo)
                .toList();
        return new BaseResponse<>(sizeVos);
    }

    @Operation(summary = "getSizeById API", description = "사이즈 상세 조회", tags = {"Size"})
    @GetMapping("/{id}")
    public BaseResponse<SizeResponseVo> getSizeById(@PathVariable Long id) {
        SizeResponseDto sizeDto = sizeService.getSizeById(id);
        return new BaseResponse<>(sizeDto.toVo());
    }
}