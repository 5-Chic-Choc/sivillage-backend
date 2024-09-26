package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.*;

import java.util.List;

public interface ProductService {

    ProductResponseDto getProduct(String productUuid);

    List<ProductResponseDto> getFilteredProducts(ProductRequestDto productRequestDto);

    ProductCountAndPageDto getFilteredProductsCount(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getTopBestProductsByCategory(ProductRequestDto productRequestDto);

    List<ProductOptionResponseDto> getProductOptions(String productUuid);

    ProductOptionResponseDto getOneProductOption(String productOptionUuid);

    List<ProductDetailResponseDto> getProductDetails(String productOptionUuid);

    List<ProductInfoResponseDto> getProductInfos(String productUuid);

    List<ProductHashtagResponseDto> getProductHashtags(String productUuid);

    FilteredProductAttributesDto getFilteredProductAttributes(ProductRequestDto productRequestDto);
}
