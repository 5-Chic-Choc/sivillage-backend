package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.*;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.*;
import com.chicchoc.sivillage.domain.product.infrastructure.*;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductDetailRepository productDetailRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ProductHashtagRepository productHashtagRepository;
    private final ProductRepositoryCustom productRepositoryCustom;

    @Override
    public ProductResponseDto getProduct(String productUuid) {
        Product product = productRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

        return ProductResponseDto.fromEntity(product);
    }

    @Override
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto dto) {

        List<Product> products = productRepositoryCustom.findFilteredProducts(dto);

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductResponseDto> getTopBestProductsByCategory(ProductRequestDto dto) {

        List<Product> products = productRepositoryCustom.findTopBestProductsByCategory(dto);

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }


    @Override
    public List<ProductOptionResponseDto> getProductOptions(String productUuid) {
        List<ProductOption> productOptions = productOptionRepository.findByProductUuid(productUuid);
        if (productOptions.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }
        return productOptions.stream()
                .map(ProductOptionResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductDetailResponseDto> getProductDetails(String productOptionUuid) {

        if (productOptionRepository.findByProductOptionUuid(productOptionUuid).isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }

        List<ProductDetail> productDetails = productDetailRepository.findByProductOptionUuid(productOptionUuid);

        return productDetails.stream()
                .map(ProductDetailResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductInfoResponseDto> getProductInfos(String productUuid) {

        if (productRepository.findByProductUuid(productUuid).isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        List<ProductInfo> productInfos = productInfoRepository.findByProductUuid(productUuid);

        return productInfos.stream()
                .map(ProductInfoResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<ProductHashtagResponseDto> getProductHashtags(String productUuid) {

        if (productRepository.findByProductUuid(productUuid).isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT);
        }

        List<ProductHashtag> productHashtags = productHashtagRepository.findByProductUuid(productUuid);

        return productHashtags.stream()
                .map(ProductHashtagResponseDto::fromEntity)
                .toList();
    }
}