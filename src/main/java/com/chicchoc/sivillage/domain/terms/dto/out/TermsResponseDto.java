package com.chicchoc.sivillage.domain.terms.dto.out;

import com.chicchoc.sivillage.domain.terms.domain.Terms;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermsResponseDto {
    private Long id;
    private Long parent;
    private Boolean isRequired;
    private String title;
    private String content;
    private List<TermsResponseDto> children = new ArrayList<>();

    public TermsResponseDto(Terms terms, List<Terms> childTerms) {
        this.id = terms.getId();
        this.parent = terms.getParent();
        this.isRequired = terms.getIsRequired();
        this.title = terms.getTitle();
        this.content = terms.getContent();

        // null 체크 후 빈 리스트로 초기화
        if (childTerms != null) {
            for (Terms childTerm : childTerms) {
                if (childTerm.getParent().equals(terms.getId()) && childTerm != null) {
                    children.add(new TermsResponseDto(childTerm, childTerms));
                }
            }
        }
    }
}