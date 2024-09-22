package com.chicchoc.sivillage.domain.terms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TermsType {
    MARKETING("마케팅"),
    SERVICE("이용약관");

    private final String type;
}
