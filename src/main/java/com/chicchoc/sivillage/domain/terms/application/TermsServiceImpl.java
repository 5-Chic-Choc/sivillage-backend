package com.chicchoc.sivillage.domain.terms.application;

import com.chicchoc.sivillage.domain.terms.domain.Terms;
import com.chicchoc.sivillage.domain.terms.domain.TermsType;
import com.chicchoc.sivillage.domain.terms.dto.in.CreateTermsRequestDto;
import com.chicchoc.sivillage.domain.terms.dto.out.TermsResponseDto;
import com.chicchoc.sivillage.domain.terms.infrastructure.TermsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;

    // 약관 생성
    public void createTerms(CreateTermsRequestDto createTermsRequestDto) {
        termsRepository.save(createTermsRequestDto.toEntity());
    }

    // 약관 조회(마케팅과 이용약관을 필터링하고, 자식 약관을 매핑)
    public Map<TermsType, List<TermsResponseDto>> getTermsHierarchy() {

        List<Terms> terms = termsRepository.findByIsViewTrue();

        Map<Long, List<Terms>> childTermsMap = mapChildTerms(terms);
        List<TermsResponseDto> marketingTerms = filterAndMapTerms(terms, TermsType.MARKETING, childTermsMap);
        List<TermsResponseDto> serviceTerms = filterAndMapTerms(terms, TermsType.SERVICE, childTermsMap);

        return createResponseMap(marketingTerms, serviceTerms);
    }

    // 자식 약관을 매핑하는 메서드
    private Map<Long, List<Terms>> mapChildTerms(List<Terms> terms) {

        return terms.stream()
                .filter(term -> Optional.ofNullable(term.getParent()).isPresent()) // 부모 약관이 있는 경우
                .collect(Collectors.groupingBy(Terms::getParent)); // 같은 부모를 가진 약관들을 그룹핑
    }

    // 특정 타입의 약관을 필터링하고, 자식 약관을 매핑하는 메서드
    private List<TermsResponseDto> filterAndMapTerms(List<Terms> terms, TermsType type,
            Map<Long, List<Terms>> childTermsMap) {
        return terms.stream()
                .filter(term -> term.getType().equals(type)) // Type 별로 필터링
                .filter(term -> !Optional.ofNullable(term.getParent()).isPresent()) // 최상위 약관만
                .map(parent -> new TermsResponseDto(parent, childTermsMap.get(parent.getId()))) // 자식 약관 매핑
                .collect(Collectors.toList()); // 리스트로 변환
    }

    // 최종적으로 응답할 Map 생성
    private Map<TermsType, List<TermsResponseDto>> createResponseMap(List<TermsResponseDto> marketingTerms,
            List<TermsResponseDto> serviceTerms) {
        Map<TermsType, List<TermsResponseDto>> responseMap = new HashMap<>();
        responseMap.put(TermsType.MARKETING, marketingTerms);
        responseMap.put(TermsType.SERVICE, serviceTerms);
        return responseMap;
    }
}
