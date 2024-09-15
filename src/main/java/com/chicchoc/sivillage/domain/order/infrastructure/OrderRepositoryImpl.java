package com.chicchoc.sivillage.domain.order.infrastructure;

import com.chicchoc.sivillage.domain.order.domain.Order;
import com.chicchoc.sivillage.domain.order.domain.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findOrderUuidByDateRangeWithUserUuid(String userUuid, LocalDateTime startDate,
            LocalDateTime endDate) {

        QOrder order = QOrder.order;

        return queryFactory
                .select(order.orderUuid)
                .from(order)
                .where(order.orderedAt.between(startDate, endDate)
                        .and(order.userUuid.eq(userUuid)))
                .fetch();

    }
}
