package com.chicchoc.sivillage.domain.order.infrastructure;

import com.chicchoc.sivillage.domain.order.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
