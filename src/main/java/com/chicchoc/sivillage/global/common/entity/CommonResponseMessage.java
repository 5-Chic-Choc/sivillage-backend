package com.chicchoc.sivillage.global.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum CommonResponseMessage {
  SUCCESS("요청에 성공"),
  FAIL("요청에 실패"),
  NOT_FOUND("해당 데이터를 찾을 수 없음"),
  UNAUTHORIZED("인증 실패"),
  INVALID_INPUT_VALUE("올바르지 않은 입력값"),
  METHOD_NOT_ALLOWED("허용되지 않은 메소드"),
  FORBIDDEN("권한 없음"),
  BAD_REQUEST("잘못된 요청"),
  INTERNAL_SERVER_ERROR("서버 내부 오류, 요청 처리 불가");

  private String message;

  CommonResponseMessage(String message) {
    this.message = message;
  }
}
