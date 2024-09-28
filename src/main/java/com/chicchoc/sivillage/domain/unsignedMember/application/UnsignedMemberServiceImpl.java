package com.chicchoc.sivillage.domain.unsignedMember.application;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import com.chicchoc.sivillage.domain.unsignedMember.dto.out.UnsignedMemberResponseDto;
import com.chicchoc.sivillage.domain.unsignedMember.infrastructure.UnsignedMemberRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsignedMemberServiceImpl implements UnsignedMemberService {

    private final UnsignedMemberRepository unsignedMemberRepository;

    @Override
    public UnsignedMemberResponseDto createUnsignedMember() {
        String uuid = NanoIdGenerator.generateNanoId();

        UnsignedMember unsignedMemberUuid = UnsignedMember.builder()
                .unsignedMemberUuid(uuid)
                .build();

        return UnsignedMemberResponseDto.fromEntity(unsignedMemberRepository.save(unsignedMemberUuid));
    }

    @Override
    public void updateUnsignedMember(String uuid) {
        UnsignedMember unsignedMember = unsignedMemberRepository.findByUnsignedMemberUuid(uuid);
        if (unsignedMember != null) {
            unsignedMember.setLastConnectedAt(LocalDateTime.now());
            unsignedMemberRepository.save(unsignedMember);
        }
    }
}
