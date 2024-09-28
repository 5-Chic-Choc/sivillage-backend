package com.chicchoc.sivillage.domain.review.infrastructure;

import com.chicchoc.sivillage.domain.review.domain.QReview;
import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.global.common.utils.CursorPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CursorPage<String> getReviewListByUserUuid(String userUuid, Long lastId, Integer pageSize,
            Integer page) {

        QReview reviewList = QReview.review;
        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(userUuid)
                .ifPresent(uuid -> builder.and(reviewList.userUuid.eq(uuid)));

        int currentPageSize = Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE);

        if (lastId != null) {
            builder.and(reviewList.id.gt(lastId));
        }

        List<Review> content = jpaQueryFactory
                .selectFrom(reviewList)
                .where(builder)
                .orderBy(reviewList.id.asc())
                .limit(currentPageSize + 1)
                .fetch();

        Long nextCursor = null;
        boolean hasNext = false;

        if (content.size() > currentPageSize) {
            hasNext = true;
            content = content.subList(0, currentPageSize);
            nextCursor = content.get(currentPageSize - 1).getId();
        }

        List<String> reviewUuids = content.stream()
                .map(Review::getReviewUuid)
                .toList();

        return new CursorPage<>(reviewUuids, nextCursor, hasNext, currentPageSize,
                Optional.ofNullable(page).orElse(DEFAULT_PAGE_NUMBER));
    }

    @Override
    public CursorPage<String> getReviewListByProductUuid(String productUuid, Long lastId, Integer pageSize,
            Integer page) {

        QReview reviewList = QReview.review;
        BooleanBuilder builder = new BooleanBuilder();

        Optional.ofNullable(productUuid)
                .ifPresent(uuid -> builder.and(reviewList.productUuid.eq(uuid)));

        int currentPageSize = Optional.ofNullable(pageSize).orElse(DEFAULT_PAGE_SIZE);

        if (lastId != null) {
            builder.and(reviewList.id.gt(lastId));
        }

        List<Review> content = jpaQueryFactory
                .selectFrom(reviewList)
                .where(builder)
                .orderBy(reviewList.id.asc())
                .limit(currentPageSize + 1)
                .fetch();

        Long nextCursor = null;
        boolean hasNext = false;

        if (content.size() > currentPageSize) {
            hasNext = true;
            content = content.subList(0, currentPageSize);
            nextCursor = content.get(currentPageSize - 1).getId();
        }

        List<String> reviewUuids = content.stream()
                .map(Review::getReviewUuid)
                .toList();

        return new CursorPage<>(reviewUuids, nextCursor, hasNext, currentPageSize,
                Optional.ofNullable(page).orElse(DEFAULT_PAGE_NUMBER));
    }
}
