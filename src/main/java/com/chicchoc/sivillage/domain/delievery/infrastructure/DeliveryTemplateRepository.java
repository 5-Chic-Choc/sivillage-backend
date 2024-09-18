package com.chicchoc.sivillage.domain.delievery.infrastructure;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTemplateRepository extends JpaRepository<DeliveryTemplate, Long> {

}
