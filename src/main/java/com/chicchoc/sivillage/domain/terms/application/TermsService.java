package com.chicchoc.sivillage.domain.terms.application;

import com.chicchoc.sivillage.domain.terms.domain.Terms;
import com.chicchoc.sivillage.domain.terms.dto.in.CreateTermsRequestDto;
import com.chicchoc.sivillage.domain.terms.dto.out.TermsResponseDto;
import com.chicchoc.sivillage.domain.terms.infrastructure.TermsRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TermsService {

    private final TermsRepository termsRepository;

    // 약관 생성
    public void createTerms(CreateTermsRequestDto createTermsRequestDto) {
        termsRepository.save(createTermsRequestDto.toEntity());
    }

    // 약관 계층 구조 조회
    public List<TermsResponseDto> getTermsHierarchy() {

        // view가 true인 약관을 모두 조회
        List<Terms> terms = termsRepository.findByIsViewTrue();

        // 부모 약관을 기준으로 자식 약관을 매핑
        Map<Long, List<Terms>> childTermsMap = terms.stream()
                .filter(term -> Optional.ofNullable(term.getParent()).isPresent())
                .collect(Collectors.groupingBy(Terms::getParent));

        // 부모 약관을 기준으로 자식 약관을 매핑한 결과를 계층 구조로 변환
        return terms.stream()
                .filter(term ->
                        !Optional.ofNullable(term.getParent()).isPresent())
                .map(parent ->
                        new TermsResponseDto(parent, childTermsMap.get(parent.getId())))
                .collect(Collectors.toList());
    }

}
