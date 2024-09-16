package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.EtcOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtcOptionRepository extends JpaRepository<EtcOption, Long> {
}