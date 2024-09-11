package com.chicchoc.sivillage.domain.unsignedMember.infrastructure;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnsignedMemberRepository extends JpaRepository<UnsignedMember, Long> {
    UnsignedMember findByUnsignedMemberUuid(String unsignedMemberUuid);
}
