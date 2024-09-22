package com.chicchoc.sivillage.domain.order.infrastructure;

import com.chicchoc.sivillage.domain.order.domain.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findByOrderUuid(String orderUuid);
}
