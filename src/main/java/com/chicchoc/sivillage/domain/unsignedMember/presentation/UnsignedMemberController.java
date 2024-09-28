package com.chicchoc.sivillage.domain.unsignedMember.presentation;

import com.chicchoc.sivillage.domain.unsignedMember.application.UnsignedMemberService;
import com.chicchoc.sivillage.domain.unsignedMember.dto.out.UnsignedMemberResponseDto;
import com.chicchoc.sivillage.domain.unsignedMember.vo.out.UnsignedMemberResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/unsignedMember")
public class UnsignedMemberController {

    private final UnsignedMemberService unsignedMemberService;

    @GetMapping
    public BaseResponse<UnsignedMemberResponseVo> getUnsignedMember(HttpServletResponse response,
            HttpServletRequest request) {
        String uuid = request.getHeader("X-Unsigned-User-UUID");

        if (uuid == null || uuid.isEmpty()) {
            UnsignedMemberResponseDto unsignedMemberDto = unsignedMemberService.createUnsignedMember();
            String newUuid = unsignedMemberDto.getUserUuid(); // 생성된 UUID 가져오기
            response.setHeader("X-Unsigned-User-UUID", newUuid);

            return new BaseResponse<>(unsignedMemberDto.toVo());
        }

        unsignedMemberService.updateUnsignedMember(uuid);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}
