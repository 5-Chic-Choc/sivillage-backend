package com.chicchoc.sivillage.domain.oauth.infrastructure;

import com.chicchoc.sivillage.domain.oauth.domain.OauthMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

    OauthMember findByOauthIdAndOauthProvider(String oauthId, String oauthProvider);

    boolean existsByOauthIdAndOauthProvider(String oauthId, String oauthProvider);

}
