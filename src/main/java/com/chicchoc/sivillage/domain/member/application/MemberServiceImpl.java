package com.chicchoc.sivillage.domain.member.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

}