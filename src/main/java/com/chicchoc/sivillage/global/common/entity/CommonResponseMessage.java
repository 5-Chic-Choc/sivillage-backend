package com.chicchoc.sivillage.global.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum CommonResponseMessage {
    //응답 메시지 표준화

    SUCCESS("요청에 성공하였습니다."),
    FAIL("요청에 실패하였습니다."),
    NOT_FOUND("해당 데이터를 찾을 수 없습니다."),
    UNAUTHORIZED("인증에 실패하였습니다."),
    INVALID_INPUT_VALUE("올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED("허용되지 않은 메소드입니다."),
    FORBIDDEN("권한이 없습니다. 다시 로그인 해주세요."),
    BAD_REQUEST("잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("서버 내부 오류, 요청을 처리 할 수 없습니다.");

    private String message;

    CommonResponseMessage(String message) {
        this.message = message;
    }
}
