package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.ProductOptionResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOptionResponseDto {
    private String productOptionUuid;
    private Long productId;
    private Long sizeId;
    private Long colorId;
    private Long etcOptionId;
    private String saleStatus;
    private int price;
    private int discountRate;
    private int discountPrice;

    public ProductOptionResponseVo toResponseVo() {
        return ProductOptionResponseVo.builder()
                .productOptionUuid(productOptionUuid)
                .productId(productId)
                .sizeId(sizeId)
                .colorId(colorId)
                .etcOptionId(etcOptionId)
                .saleStatus(saleStatus)
                .price(price)
                .discountRate(discountRate)
                .discountPrice(discountPrice)
                .build();
    }


}
