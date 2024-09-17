package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ProductMediaService;
import com.chicchoc.sivillage.domain.product.dto.out.ProductMediaResponseDto;
import com.chicchoc.sivillage.domain.product.vo.out.ProductMediaResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-media")
public class ProductMediaController {

    private final ProductMediaService productMediaService;

    @Operation(summary = "getMediasByUuid API", description = "옵션 별 미디어 조회", tags = {"Product Media"})
    @GetMapping("/{productOptionUuid}")
    public BaseResponse<List<ProductMediaResponseVo>> getProductMediaByOptionUuid(
            @PathVariable String productOptionUuid) {
        List<ProductMediaResponseDto> responseDtos = productMediaService
                .getProductMediaByOptionUuid(productOptionUuid);

        List<ProductMediaResponseVo> responseVos = responseDtos.stream()
                .map(dto -> ProductMediaResponseVo.builder()
                        .id(dto.getId())
                        .productOptionUuid(dto.getProductOptionUuid())
                        .mediaId(dto.getMediaId())
                        .mediaOrder(dto.getMediaOrder())
                        .build())
                .collect(Collectors.toList());

        return new BaseResponse<>(responseVos);
    }
}