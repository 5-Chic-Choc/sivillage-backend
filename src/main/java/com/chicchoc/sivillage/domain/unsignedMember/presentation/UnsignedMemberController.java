package com.chicchoc.sivillage.domain.unsignedMember.presentation;

import com.chicchoc.sivillage.domain.unsignedMember.application.UnsignedMemberService;
import com.chicchoc.sivillage.domain.unsignedMember.vo.out.UnsignedMemberResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/unsignedMember")
public class UnsignedMemberController {

    private final UnsignedMemberService unsignedMemberService;

    @Operation(summary = "getUnsignedMember API", description = "비회원 조회", tags = {"비회원"})
    @GetMapping
    public BaseResponse<UnsignedMemberResponseVo> getUnsignedMember() {
        return new BaseResponse<>(unsignedMemberService.createUnsignedMember().toVo());
    }

    @PutMapping
    public void updateLastConnectedAt(HttpServletRequest request) {
        unsignedMemberService.updateUnsignedMember(request.getHeader("X-Unsigned-User-UUID"));
    }

}
