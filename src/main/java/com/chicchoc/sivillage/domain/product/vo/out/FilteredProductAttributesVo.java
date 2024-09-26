package com.chicchoc.sivillage.domain.product.vo.out;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FilteredProductAttributesVo {

    private List<String> colorIds;

    private List<String> sizeIds;

    private List<String> brandUuids;
}
