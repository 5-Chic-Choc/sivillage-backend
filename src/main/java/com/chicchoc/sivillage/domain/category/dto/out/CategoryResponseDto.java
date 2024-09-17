package com.chicchoc.sivillage.domain.category.dto.out;

import com.chicchoc.sivillage.domain.category.vo.out.CategoryResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDto {
    private Long id;
    private String name;
    private Integer depth;

    public CategoryResponseVo toResponseVo() {
        return CategoryResponseVo.builder()
                .id(this.id)
                .name(this.name)
                .depth(this.depth)
                .build();
    }
}