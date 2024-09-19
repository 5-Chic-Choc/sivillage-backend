package com.chicchoc.sivillage.domain.terms.presentation;

import com.chicchoc.sivillage.domain.terms.application.TermsService;
import com.chicchoc.sivillage.domain.terms.domain.TermsType;
import com.chicchoc.sivillage.domain.terms.dto.in.CreateTermsRequestDto;
import com.chicchoc.sivillage.domain.terms.dto.out.TermsResponseDto;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "약관 관련", description = "약관 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/terms")
public class TermsController {

    private final TermsService termsService;

    @Operation(summary = "약관 추가(관리자 ONLY)", description = "관리자용 약관데이터 생성")
    @PostMapping("/create")
    public BaseResponse<Void> createTerms(@RequestBody CreateTermsRequestDto createTermsRequestDto) {

        termsService.createTerms(createTermsRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "약관 조회", description = "@Return Map<TermsType, List<Terms>")
    @GetMapping()
    public BaseResponse<Map<TermsType, List<TermsResponseDto>>> getTerms() {

        Map<TermsType, List<TermsResponseDto>> terms = termsService.getTermsHierarchy();

        return new BaseResponse<>(terms);
    }


}
