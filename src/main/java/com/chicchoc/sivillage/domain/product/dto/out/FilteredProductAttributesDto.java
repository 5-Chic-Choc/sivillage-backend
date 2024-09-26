package com.chicchoc.sivillage.domain.product.dto.out;

import com.chicchoc.sivillage.domain.product.vo.out.FilteredProductAttributesVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FilteredProductAttributesDto {

    private List<String> colorIds;

    private List<String> sizeIds;

    private List<String> brandUuids;

    public FilteredProductAttributesVo toVo() {
        return FilteredProductAttributesVo.builder()
                .colorIds(colorIds)
                .sizeIds(sizeIds)
                .brandUuids(brandUuids)
                .build();
    }
}
