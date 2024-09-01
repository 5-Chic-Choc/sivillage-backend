package com.chicchoc.sivillage.domain.member.application;

import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;

public interface MemberService {

  //todo
  void findMemberByUuid(String uuid);
  void findMemberByEmail(String email);
}
