package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.product.domain.*;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.*;
import com.chicchoc.sivillage.domain.product.infrastructure.*;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.querydsl.core.types.OrderSpecifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto dto) {
        final int page = dto.getPage() != null ? dto.getPage() : 1;
        final int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
        final int offset = (page - 1) * perPage;
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(dto.getSortBy(), dto.isAscending());

        List<Product> products = productRepositoryCustom.findFilteredProducts(dto, offset, perPage, orderSpecifier);

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortBy, boolean isAscending) {
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;

        switch (sortBy) {
            case "discount_rate":
                return isAscending ? productOption.discountRate.asc() : productOption.discountRate.desc();
            case "price":
                return isAscending ? productOption.price.asc() : productOption.price.desc();
            case "name":
                return isAscending ? product.productName.asc() : product.productName.desc();
            case "createdAt":
                return isAscending ? product.createdAt.asc() : product.createdAt.desc();
            default:
                throw new BaseException(BaseResponseStatus.INVALID_SORT_BY_PARAMETER);
        }
    }

    @Override
    public Long findProductIdByUuid(String productUuid) {
        Product product = productRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        return product.getId();
    }

    @Override
    public List<ProductOptionResponseDto> getProductOptions(Long productId) {
        List<ProductOption> productOptions = productOptionRepository.findByProductId(productId);
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