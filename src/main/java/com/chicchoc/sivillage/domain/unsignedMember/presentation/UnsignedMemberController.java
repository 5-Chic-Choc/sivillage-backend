package com.chicchoc.sivillage.domain.unsignedMember.presentation;

import com.chicchoc.sivillage.domain.unsignedMember.application.UnsignedMemberService;
import jakarta.servlet.http.Cookie;
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
    public void getUnsignedMember(HttpServletResponse response, HttpServletRequest request) {
        String uuid = request.getHeader("uuid");
        if(uuid == null) { // uuid 존재하지 않는다면
            String UnsignedMemberUuid = unsignedMemberService.createUnsignedMember();
            response.setHeader("uuid", UnsignedMemberUuid);
        }
        else { // uuid 존재한다면
            unsignedMemberService.updateUnsignedMember(uuid);
        }
    }
}
