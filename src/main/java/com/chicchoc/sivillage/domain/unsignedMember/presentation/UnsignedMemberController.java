package com.chicchoc.sivillage.domain.unsignedMember.presentation;

import com.chicchoc.sivillage.domain.unsignedMember.application.UnsignedMemberService;
import com.chicchoc.sivillage.global.common.entity.CommonResponseEntity;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/unsignedMember")
public class UnsignedMemberController {

    private final UnsignedMemberService unsignedMemberService;

    @GetMapping
    public void getUnsignedMember(
            @CookieValue(value = "uuid", required = false) String uuid, HttpServletResponse response) {
        if(uuid == null) { // uuid 존재하지 않는다면
            String UnsignedMemberUuid = unsignedMemberService.createUnsignedMember();

            Cookie uuidCookie = new Cookie("uuid", UnsignedMemberUuid);
            uuidCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 유지
            uuidCookie.setHttpOnly(true);
            uuidCookie.setPath("/");
            response.addCookie(uuidCookie);
        }
        else { // uuid 존재한다면
            unsignedMemberService.updateUnsignedMember(uuid);

            Cookie uuidCookie = new Cookie("uuid", uuid);
            uuidCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 유지
            uuidCookie.setHttpOnly(true);
            uuidCookie.setPath("/");
            response.addCookie(uuidCookie);
        }

    }
}
