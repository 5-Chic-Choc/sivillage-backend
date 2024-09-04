package com.chicchoc.sivillage.domain.member.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    //todo : implement member service

    @Override
    public Member findMemberByUuid(String uuid) {
        return memberRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

}