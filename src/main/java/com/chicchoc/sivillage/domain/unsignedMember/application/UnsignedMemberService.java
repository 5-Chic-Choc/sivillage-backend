package com.chicchoc.sivillage.domain.unsignedMember.application;

import com.chicchoc.sivillage.domain.unsignedMember.dto.out.UnsignedMemberResponseDto;

public interface UnsignedMemberService {

    UnsignedMemberResponseDto createUnsignedMember();

    void updateUnsignedMember(String uuid);
}
