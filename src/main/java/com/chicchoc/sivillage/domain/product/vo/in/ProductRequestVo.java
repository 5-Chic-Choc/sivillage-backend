package com.chicchoc.sivillage.domain.product.vo.in;

import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductRequestVo {
    private List<String> categories;
    private List<String> sizes;
    private List<String> colors;
    private List<String> brands;
    private Integer minimumPrice;
    private Integer maximumPrice;
    private int page;
    private int perPage;
    private String sortBy;
    private boolean isAscending;

    // DTO로 변환하는 메소드
    public ProductRequestDto toRequestDto() {
        return ProductRequestDto.builder()
                .categories(categories)
                .sizes(sizes)
                .colors(colors)
                .brands(brands)
                .minimumPrice(minimumPrice)
                .maximumPrice(maximumPrice)
                .page(page)
                .perPage(perPage)
                .sortBy(sortBy)
                .isAscending(isAscending)
                .build();
    }
}
