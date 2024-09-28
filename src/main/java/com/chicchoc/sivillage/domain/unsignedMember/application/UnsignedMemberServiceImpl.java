package com.chicchoc.sivillage.domain.unsignedMember.application;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import com.chicchoc.sivillage.domain.unsignedMember.dto.out.UnsignedMemberResponseDto;
import com.chicchoc.sivillage.domain.unsignedMember.infrastructure.UnsignedMemberRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsignedMemberServiceImpl implements UnsignedMemberService {

    private final UnsignedMemberRepository unsignedMemberRepository;

    @Override
    public UnsignedMemberResponseDto createUnsignedMember() {

        return UnsignedMemberResponseDto.fromEntity(unsignedMemberRepository.save(UnsignedMember.builder()
                .unsignedMemberUuid(NanoIdGenerator.generateNanoId())
                .build()));
    }

    @Override
    public void updateUnsignedMember(String uuid) {

        UnsignedMember unsignedMember = unsignedMemberRepository.findByUnsignedMemberUuid(uuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        unsignedMemberRepository.save(UnsignedMember.builder()
                .id(unsignedMember.getId())
                .createdAt(unsignedMember.getCreatedAt())
                .unsignedMemberUuid(uuid)
                .lastConnectedAt(LocalDateTime.now())
                .build());

    }
}
