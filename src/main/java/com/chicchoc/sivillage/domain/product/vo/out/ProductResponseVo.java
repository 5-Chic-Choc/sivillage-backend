package com.chicchoc.sivillage.domain.product.vo.out;

import com.chicchoc.sivillage.domain.product.dto.out.ProductOptionResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ProductResponseVo {
    private String productUuid;
    private String brandUuid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductOptionResponseDto> productOptions;
}
