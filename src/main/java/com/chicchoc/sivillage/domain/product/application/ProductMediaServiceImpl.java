package com.chicchoc.sivillage.domain.product.application;


import com.chicchoc.sivillage.domain.product.domain.ProductMedia;
import com.chicchoc.sivillage.domain.product.dto.out.ProductMediaResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductMediaRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMediaServiceImpl implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;

    @Override
    public List<ProductMediaResponseDto> getProductMediaByOptionUuid(String productOptionUuid) {
        List<ProductMedia> productMediaList = productMediaRepository.findByProductOptionUuid(productOptionUuid);

        if (productMediaList.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        return productMediaList.stream()
                .map(ProductMediaResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}