package com.chicchoc.sivillage.domain.member.application;

import com.chicchoc.sivillage.domain.member.domain.Member;

public interface MemberService {

    Member findMemberByUuid(String uuid);

    Member findMemberByEmail(String email);
}
