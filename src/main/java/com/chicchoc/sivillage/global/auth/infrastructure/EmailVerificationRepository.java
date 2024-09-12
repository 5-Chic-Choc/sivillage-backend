package com.chicchoc.sivillage.global.auth.infrastructure;


import com.chicchoc.sivillage.global.auth.domain.EmailVerification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findTopByEmailOrderByIdDesc(String email);

    void deleteByEmail(String email);
}
