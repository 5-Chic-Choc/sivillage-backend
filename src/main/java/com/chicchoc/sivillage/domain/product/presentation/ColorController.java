package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ColorService;
import com.chicchoc.sivillage.domain.product.dto.out.ColorResponseDto;
import com.chicchoc.sivillage.domain.product.vo.out.ColorResponseVo;
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
@RequestMapping("/api/v1/colors")
public class ColorController {

    private final ColorService colorService;

    @Operation(summary = "getAllColors API", description = "모든 색상 조회", tags = {"색상"})
    @GetMapping
    public BaseResponse<List<ColorResponseVo>> getAllColors() {
        List<ColorResponseDto> colorDtos = colorService.getAllColors();
        List<ColorResponseVo> colorVos = colorDtos.stream()
                .map(ColorResponseDto::toVo)
                .toList();
        return new BaseResponse<>(colorVos);
    }

    @Operation(summary = "getColorById API", description = "색상 상세 조회", tags = {"색상"})
    @GetMapping("/{id}")
    public BaseResponse<ColorResponseVo> getColorById(@PathVariable Long id) {
        ColorResponseDto colorDto = colorService.getColorById(id);
        return new BaseResponse<>(colorDto.toVo());
    }
}