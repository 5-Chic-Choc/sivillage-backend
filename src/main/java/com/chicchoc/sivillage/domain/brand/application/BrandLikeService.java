package com.chicchoc.sivillage.domain.brand.application;

import java.util.List;

public interface BrandLikeService {

    void saveAndUpdateBrandLike(String brandUuid, String userUuid);

    Boolean isLikedBrand(String brandUuid, String userUuid);

    List<String> getLikedBrandList(String userUuid);
}
