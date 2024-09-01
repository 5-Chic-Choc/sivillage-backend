package com.chicchoc.sivillage.global.auth.infrastructure;

import com.chicchoc.sivillage.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

}
