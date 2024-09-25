package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductCountAndPageVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCountAndPageDto {

    private Long totalCount;

    private Integer totalPages;

    public ProductCountAndPageVo toVo() {
        return ProductCountAndPageVo.builder()
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
    }
}
