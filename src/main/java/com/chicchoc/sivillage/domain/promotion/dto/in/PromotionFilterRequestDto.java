package com.chicchoc.sivillage.domain.promotion.dto.in;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PromotionFilterRequestDto {

    private Long categoryId;
    private List<String> benefitTypes;
    private List<String> brandUuids;
    private Integer page;
    private Integer perPage;
}
