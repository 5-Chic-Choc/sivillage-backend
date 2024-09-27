package com.chicchoc.sivillage.domain.review.infrastructure;

import com.chicchoc.sivillage.global.common.utils.CursorPage;

public interface ReviewRepositoryCustom {

    CursorPage<String> getReviewListByUserUuid(String useUuid, Long lastId, Integer pageSize, Integer page);

    CursorPage<String> getReviewListByProductUuid(String productUuid, Long lastId, Integer pageSize, Integer page);
}
