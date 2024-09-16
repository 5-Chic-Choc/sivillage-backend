package com.chicchoc.sivillage.domain.terms.presentation;

import com.chicchoc.sivillage.domain.terms.application.TermsService;
import com.chicchoc.sivillage.domain.terms.dto.in.CreateTermsRequestDto;
import com.chicchoc.sivillage.domain.terms.dto.out.TermsResponseDto;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
    //todo 약관 추가 api


    @Operation(summary = "약관 추가", description = "약관 추가(데이터 생성용)")
    @PostMapping("/create")
    public BaseResponse<Void> createTerms(@RequestBody CreateTermsRequestDto createTermsRequestDto) {

        termsService.createTerms(createTermsRequestDto);

        return new BaseResponse<>();
    }

    //todo 약관 조회 api(view 상태가 1인 리스트 조회)
    @Operation(summary = "약관 조회", description = "약관 전체 조회")
    @GetMapping()
    public BaseResponse<List<TermsResponseDto>> getTerms() {

        List<TermsResponseDto> terms = termsService.getTermsHierarchy();

        return new BaseResponse<>(terms);
    }


}
