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
import java.util.Map;
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
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

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
    public ProductCountAndPageDto getFilteredProductsCount(ProductRequestDto dto) {
        return productRepositoryCustom.findFilteredProductsCount(dto);
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
    public ProductOptionResponseDto getOneProductOption(String productOptionUuid) {

        ProductOption productOption = productOptionRepository.findByProductOptionUuid(productOptionUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

        return ProductOptionResponseDto.fromEntity(productOption);
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

    @Override
    public FilteredProductAttributesDto getFilteredProductAttributes(ProductRequestDto dto) {
        return productRepositoryCustom.findFilteredProductAttributes(dto);
    }

    @Override
    public Map<ColorResponseDto, List<SizeResponseDto>> getColorSizeMappingByProductUuid(String productUuid) {

        List<ProductOption> productOptions = productOptionRepository.findByProductUuid(productUuid);

        if (productOptions.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }

        List<Long> colorIds = productOptions.stream()
                .map(ProductOption::getColorId)
                .distinct()
                .toList();

        List<Long> sizeIds = productOptions.stream()
                .map(ProductOption::getSizeId)
                .distinct()
                .toList();

        List<Color> colors = colorRepository.findByIdIn(colorIds);
        List<Size> sizes = sizeRepository.findByIdIn(sizeIds);

        return colors.stream().collect(Collectors.toMap(
                color -> ColorResponseDto.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .value(color.getValue())
                        .build(),
                color -> productOptions.stream()
                        .filter(po -> po.getColorId().equals(color.getId()))
                        .map(po -> sizes.stream()
                                .filter(size -> size.getId().equals(po.getSizeId()))
                                .findFirst()
                                .map(size -> SizeResponseDto.builder()
                                        .id(size.getId())
                                        .name(size.getName())
                                        .value(size.getValue())
                                        .build())
                                .orElse(null))
                        .filter(sizeDto -> sizeDto != null)
                        .toList()
        ));
    }

}