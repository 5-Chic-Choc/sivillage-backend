package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.ProductOption;
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
    private Integer price;
    private Integer discountRate;
    private Integer discountPrice;

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

    public static ProductOptionResponseDto fromEntity(ProductOption productOption) {
        return ProductOptionResponseDto.builder()
                .productOptionUuid(productOption.getProductOptionUuid())
                .productId(productOption.getProduct() != null ? productOption.getProduct().getId() : null)
                .sizeId(productOption.getSizeId())
                .colorId(productOption.getColorId())
                .etcOptionId(productOption.getEtcOptionId())
                .saleStatus(productOption.getSaleStatus() != null ? productOption.getSaleStatus().name() : null)
                .price(productOption.getPrice())
                .discountRate(productOption.getDiscountRate())
                .discountPrice(productOption.getDiscountPrice())
                .build();
    }

}
