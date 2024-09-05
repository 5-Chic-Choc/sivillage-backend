package com.chicchoc.sivillage.domain.product.vo.in;

import com.chicchoc.sivillage.domain.product.dto.in.ProductDetailRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailRequestVo {
    private String productUuid;
}
