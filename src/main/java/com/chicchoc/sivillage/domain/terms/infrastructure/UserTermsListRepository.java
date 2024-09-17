package com.chicchoc.sivillage.domain.terms.infrastructure;

import com.chicchoc.sivillage.domain.terms.domain.UserTermsList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermsListRepository extends JpaRepository<UserTermsList, Long> {

}
