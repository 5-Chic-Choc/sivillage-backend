package com.chicchoc.sivillage.domain.terms.infrastructure;

import com.chicchoc.sivillage.domain.terms.domain.Terms;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermsRepository extends JpaRepository<Terms, Long> {

    List<Terms> findByIsViewTrue();

}
