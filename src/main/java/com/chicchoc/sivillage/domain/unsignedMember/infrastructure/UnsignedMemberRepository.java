package com.chicchoc.sivillage.domain.unsignedMember.infrastructure;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnsignedMemberRepository extends JpaRepository<UnsignedMember, Long> {
    Optional<UnsignedMember> findByUnsignedMemberUuid(String unsignedMemberUuid);
}
