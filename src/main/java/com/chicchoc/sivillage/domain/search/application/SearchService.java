package com.chicchoc.sivillage.domain.search.application;

import java.util.List;

public interface SearchService {
    void addRecentSearch(String userUuid, String keyword);

    List<Object> getRecentSearches(String userUuid);

    void deleteRecentSearch(String userUuid, String keyword);

    void addRecentViewedProduct(String userUuid, String productUuid);

    List<Object> getRecentViewedProducts(String userUuid);

    void deleteRecentViewedProduct(String userUuid, String productUuid);
}
