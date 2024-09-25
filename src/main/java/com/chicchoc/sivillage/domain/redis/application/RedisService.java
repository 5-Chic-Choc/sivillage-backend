package com.chicchoc.sivillage.domain.redis.application;

import java.util.List;

public interface RedisService {
    void addRecentSearch(String userUuid, String keyword);

    List<Object> getRecentSearches(String userUuid);

    void deleteRecentSearch(String userUuid, String keyword);

    void addRecentViewedProduct(String userUuid, String productUuid);

    List<Object> getRecentViewedProducts(String userUuid);

    void deleteRecentViewedProduct(String userUuid, String productUuid);
}
