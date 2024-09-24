package com.chicchoc.sivillage.domain.redis.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final int MAX_RECENT_ITEMS = 30; // 최대 저장할 개수

    // 최근 검색어 추가
    public void addRecentSearch(String userUuid, String keyword) {

        String key = "recentSearch:" + userUuid;

        // 동일한 검색어가 있을 경우 제거
        redisTemplate.opsForList().remove(key, 0, keyword);

        redisTemplate.opsForList().leftPush(key, keyword);
        redisTemplate.expire(key, 7, TimeUnit.DAYS); // 7일간 유지

        Long listSize = redisTemplate.opsForList().size(key);
        log.info("최근 검색어 수: {}", listSize); // 로그 추가

        // 최대 길이를 넘어가면 삭제
        if (listSize != null && listSize > MAX_RECENT_ITEMS) {
            redisTemplate.opsForList().trim(key, 0, MAX_RECENT_ITEMS - 1);
            log.info("바뀐 리스트 길이: {}", MAX_RECENT_ITEMS); // 로그 추가
        }
    }

    // 최근 검색어 조회
    public List<Object> getRecentSearches(String userUuid) {

        String key = "recentSearch:" + userUuid;

        return redisTemplate.opsForList().range(key, 0, 9);
    }

    // 최근 검색어 삭제
    public void deleteRecentSearch(String userUuid, String keyword) {
        String key = "recentSearch:" + userUuid;
        redisTemplate.opsForList().remove(key, 0, keyword);
    }

    // 최근 본 상품 추가
    public void addRecentViewedProduct(String userUuid, String productUuid) {

        String key = "recentViewed:" + userUuid;

        // 동일한 상품이 있을 경우 제거
        redisTemplate.opsForList().remove(key, 0, productUuid);

        redisTemplate.opsForList().leftPush(key, productUuid);
        redisTemplate.expire(key, 7, TimeUnit.DAYS); // 7일간 유지

        Long listSize = redisTemplate.opsForList().size(key);
        log.info("최근 본 상품 수: {}", listSize); // 로그 추가

        // 최대 길이를 넘어가면 삭제
        if (listSize != null && listSize > MAX_RECENT_ITEMS) {
            redisTemplate.opsForList().trim(key, 0, MAX_RECENT_ITEMS - 1);
            log.info("바뀐 상품 리스트 길이: {}", MAX_RECENT_ITEMS); // 로그 추가
        }
    }

    // 최근 본 상품 조회
    public List<Object> getRecentViewedProducts(String userUuid) {

        String key = "recentViewed:" + userUuid;

        return redisTemplate.opsForList().range(key, 0, -1);
    }

    // 최근 검색어 삭제
    public void deleteRecentViewedProduct(String userUuid, String productUuid) {
        String key = "recentViewed:" + userUuid;
        redisTemplate.opsForList().remove(key, 0, productUuid);
    }
}