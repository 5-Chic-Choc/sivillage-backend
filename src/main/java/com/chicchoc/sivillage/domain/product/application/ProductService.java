package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.*;

import java.util.List;

public interface ProductService {

    List<ProductResponseDto> getFilteredProducts(ProductRequestDto productRequestDto);

    Long findProductIdByUuid(String productUuid);

    List<ProductOptionResponseDto> getProductOptions(Long productId);

    List<ProductDetailResponseDto> getProductDetails(String productOptionUuid);

    List<ProductInfoResponseDto> getProductInfos(String productUuid);

    List<ProductHashtagResponseDto> getProductHashtags(String productUuid);
}
