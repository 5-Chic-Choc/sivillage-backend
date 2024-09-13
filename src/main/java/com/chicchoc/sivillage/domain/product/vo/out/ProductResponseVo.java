package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProductResponseVo {
    private Long productId;
    private String productUuid;
    private String brandUuid;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
