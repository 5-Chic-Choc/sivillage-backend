package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {


    private final MemberRepository memberRepository;

    @Override
    // uuid로 사용자 정보를 가져오는 메서드
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        return memberRepository.findByUuid(uuid).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_USER)
        );
    }
}
