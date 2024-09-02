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
@RequestMapping("/api/v1")
public class BrandController {

    private final BrandService brandService;

    @Operation(summary = "getBrand API", description = "브랜드 조회", tags = {"Brand"})
    @GetMapping("/main/brand")
    public CommonResponseEntity<BrandResponseVo> getBrands() {
        List<BrandResponseDto> brandResponseDtos = brandService.getBrands();

        return new CommonResponseEntity<>(
                HttpStatus.OK,
                "브랜드 조회 성공",
                BrandResponseVo.builder()
                        .brands(brandResponseDtos)
                        .build()
        );
    }
}
