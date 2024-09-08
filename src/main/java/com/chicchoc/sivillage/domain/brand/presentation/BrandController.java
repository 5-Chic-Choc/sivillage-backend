package com.chicchoc.sivillage.domain.brand.presentation;

import com.chicchoc.sivillage.domain.brand.application.BrandService;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;
import com.chicchoc.sivillage.domain.brand.vo.out.BrandResponseVo;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "getAllBrands API", description = "전체 브랜드 조회", tags = {"Brand"})
    @GetMapping()
    public CommonResponseEntity<List<BrandResponseVo>> getBrands() {
        List<BrandResponseDto> brandResponseDtos = brandService.findAllBrands();

        List<BrandResponseVo> brandResponseVos = brandResponseDtos.stream()
                .map(BrandResponseDto::toResponseVo)
                .toList();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "브랜드 조회 성공",
                brandResponseVos
        );
    }
}
