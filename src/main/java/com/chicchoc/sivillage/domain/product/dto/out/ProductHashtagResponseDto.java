package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.domain.ProductHashtag;
import com.chicchoc.sivillage.domain.product.vo.out.ProductHashtagResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductHashtagResponseDto {
    private Long id;

    private String hashtagContent;

    public ProductHashtagResponseVo toVo() {
        return ProductHashtagResponseVo.builder()
                .id(this.id)
                .hashtagContent(this.hashtagContent)
                .build();
    }

    public static ProductHashtagResponseDto fromEntity(ProductHashtag productHashtag) {
        return ProductHashtagResponseDto.builder()
                .id(productHashtag.getId())
                .hashtagContent(productHashtag.getHashtagContent())
                .build();
    }

}
