package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.ProductInfo;
import com.chicchoc.sivillage.domain.product.vo.out.ProductInfoResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfoResponseDto {
    private Long id;

    private String kind;

    private String value;

    private boolean isFiltered;

    public ProductInfoResponseVo toVo() {
        return ProductInfoResponseVo.builder()
                .id(this.id)
                .kind(this.kind)
                .value(this.value)
                .isFiltered(this.isFiltered)
                .build();
    }

    public static ProductInfoResponseDto fromEntity(ProductInfo productInfo) {
        return ProductInfoResponseDto.builder()
                .id(productInfo.getId())
                .kind(productInfo.getKind())
                .value(productInfo.getValue())
                .isFiltered(productInfo.isFiltered())
                .build();
    }
}
