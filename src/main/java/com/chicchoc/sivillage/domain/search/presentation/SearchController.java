package com.chicchoc.sivillage.domain.search.presentation;

import com.chicchoc.sivillage.domain.search.application.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/redis")
public class RedisController {

    private final RedisService redisService;

    @Operation(summary = "최근 검색어 조회 API", description = "최근 검색어 목록 조회", tags = {"Redis"})
    @GetMapping("/recent/searched")
    public List<Object> getRecentSearches(@RequestParam String userUuid) {
        return redisService.getRecentSearches(userUuid);
    }

    // 최근 검색어 삭제
    @Operation(summary = "최근 검색어 삭제 API", description = "최근 검색어 삭제", tags = {"Redis"})
    @DeleteMapping("/recent/searched")
    public void deleteRecentSearch(@RequestParam String userUuid, @RequestParam String keyword) {
        redisService.deleteRecentSearch(userUuid, keyword);
        log.info("검색어 '{}'가 '{}' 유저의 목록에서 삭제되었습니다.", keyword, userUuid);
    }

    @Operation(summary = "최근 본 상품 조회 API", description = "최근 본 상품 목록 조회", tags = {"Redis"})
    @GetMapping("/recent/viewed")
    public List<Object> getRecentViewedProducts(@RequestParam String userUuid) {
        return redisService.getRecentViewedProducts(userUuid);
    }

    // 최근 본 상품 삭제
    @Operation(summary = "최근 본 상품 삭제 API", description = "최근 본 상품 삭제", tags = {"Redis"})
    @DeleteMapping("/recent/viewed")
    public void deleteRecentViewedProduct(@RequestParam String userUuid, @RequestParam String productUuid) {
        redisService.deleteRecentViewedProduct(userUuid, productUuid);
        log.info("상품 '{}'가 '{}' 유저의 목록에서 삭제되었습니다.", productUuid, userUuid);
    }
}
