package com.chicchoc.sivillage.domain.member.infrastructure;


import com.chicchoc.sivillage.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByUuid(String uuid);

    boolean existsByEmail(String email);

    boolean existsByNameAndPhone(String name, String phone);

    // email만 가져오기(이름, 전화번호로)
    @Query("SELECT m.email FROM Member m WHERE m.name = :name AND m.phone = :phoneNumber")
    Optional<String> findEmailByNameAndPhoneNumber(@Param("name") String name,
            @Param("phoneNumber") String phoneNumber);


}
