package com.chicchoc.sivillage.domain.delievery.infrastructure;

import com.chicchoc.sivillage.domain.delievery.domain.DeliveryTemplate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryTemplateRepository extends JpaRepository<DeliveryTemplate, Long> {
    List<DeliveryTemplate> findByUserUuid(String userUuid);

    DeliveryTemplate findByTemplateUuid(String deliveryTemplateUuid);
}
