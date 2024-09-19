package com.chicchoc.sivillage.domain.terms.application;

import com.chicchoc.sivillage.domain.terms.domain.TermsType;
import com.chicchoc.sivillage.domain.terms.dto.in.CreateTermsRequestDto;
import com.chicchoc.sivillage.domain.terms.dto.out.TermsResponseDto;
import java.util.List;
import java.util.Map;

public interface TermsService {

    void createTerms(CreateTermsRequestDto createTermsRequestDto);

    Map<TermsType, List<TermsResponseDto>> getTermsHierarchy();

}
