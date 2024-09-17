package com.chicchoc.sivillage.domain.order.infrastructure;

import com.chicchoc.sivillage.domain.order.domain.Order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepositoryCustom {
    List<String> findOrderUuidByDateRangeWithUserUuid(String userUuid, LocalDateTime startDate, LocalDateTime endDate);
}
