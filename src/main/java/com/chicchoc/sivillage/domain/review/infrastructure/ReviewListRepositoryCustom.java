package com.chicchoc.sivillage.domain.review.infrastructure;

import com.chicchoc.sivillage.global.common.utils.CursorPage;

public interface ReviewListRepositoryCustom {
    CursorPage<String> getReviewList(String productUuid, String useUuid, Long lastId, Integer pageSize, Integer page);
}
