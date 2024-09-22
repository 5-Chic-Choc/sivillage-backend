package com.chicchoc.sivillage.domain.brand.application;

import com.chicchoc.sivillage.domain.brand.domain.BrandLike;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandLikeServiceImpl implements BrandLikeService {

    private final BrandLikeRepository brandLikeRepository;

    @Transactional
    @Override
    public void saveAndUpdateBrandLike(String brandUuid, String userUuid) {

        // 1. 좋아요가 있는지 확인
        BrandLike brandLike =
                brandLikeRepository.findTopByBrandUuidAndUserUuid(
                                brandUuid,
                                userUuid)
                        .orElse(null);

        // 2. 좋아요가 있으면 수정
        if (brandLike != null) {
            brandLike.update(!brandLike.getIsLiked());
        } else {
            // 3. 좋아요가 없으면 저장
            brandLikeRepository.save(new BrandLike(brandUuid, userUuid, true));
        }
    }

    @Transactional
    @Override
    public Boolean isLikedBrand(String brandUuid, String userUuid) {

        BrandLike brandLike = brandLikeRepository.findTopByBrandUuidAndUserUuidAndIsLiked(
                        brandUuid,
                        userUuid,
                        true)
                .orElse(null);

        return brandLike != null; // 좋아요가 있으면 true, 없으면 false
    }

    @Transactional
    @Override
    public List<String> getLikedBrandList(String userUuid) {

        List<BrandLike> likedBrandList = brandLikeRepository.findByUserUuidAndIsLiked(userUuid, true);

        // 최근 좋아요한 순으로 정렬
        Collections.reverse(likedBrandList);

        // 브랜드 UUID 리스트 반환
        return likedBrandList.stream()
                .map(BrandLike::getBrandUuid)
                .toList();
    }
}
