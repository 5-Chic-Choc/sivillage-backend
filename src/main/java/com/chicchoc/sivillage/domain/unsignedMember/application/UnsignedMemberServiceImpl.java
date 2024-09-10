package com.chicchoc.sivillage.domain.unsignedMember.application;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import com.chicchoc.sivillage.domain.unsignedMember.dto.in.UnsignedMemberRequestDto;
import com.chicchoc.sivillage.domain.unsignedMember.infrastructure.UnsignedMemberRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnsignedMemberServiceImpl implements UnsignedMemberService {

    private final UnsignedMemberRepository unsignedMemberRepository;
    private final NanoIdGenerator nanoIdGenerator;

    @Override
    public String createUnsignedMember() {
        String uuid = nanoIdGenerator.generateNanoId();

        UnsignedMember unsignedMemberUuid = UnsignedMember.builder()
                .unsignedMemberUuid(uuid)
                .build();

        unsignedMemberRepository.save(unsignedMemberUuid);

        return uuid;
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
