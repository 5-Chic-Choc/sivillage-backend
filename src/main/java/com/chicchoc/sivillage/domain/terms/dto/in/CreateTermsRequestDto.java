package com.chicchoc.sivillage.domain.terms.dto.in;

import com.chicchoc.sivillage.domain.terms.domain.Terms;
import lombok.Getter;

@Getter
public class CreateTermsRequestDto {
    private Long parent;
    private Boolean isRequired;
    private String title;
    private String content;
    private Boolean isView;

    public Terms toEntity() {
        return Terms.builder()
                .parent(parent)
                .isRequired(isRequired)
                .title(title)
                .content(content)
                .isView(isView)
                .build();
    }
}
