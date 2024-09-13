package com.chicchoc.sivillage.domain.product.dto.in;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductRequestDto {
    private List<String> categories;
    private Integer depth;
    private List<String> sizes;
    private List<String> colors;
    private List<String> brands;  // 필드를 복수형으로 수정
    private Integer minimumPrice;
    private Integer maximumPrice;
    private Integer page;
    private Integer perPage;
    private String sortBy;
    private boolean isAscending;
}

