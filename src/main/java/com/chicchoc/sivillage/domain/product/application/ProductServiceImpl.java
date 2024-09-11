package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.QProduct;
import com.chicchoc.sivillage.domain.product.domain.QProductOption;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductOptionResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final JPAQueryFactory queryFactory;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;

    @Override
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto productRequestDto) {
        QProduct productQuery = QProduct.product;
        QProductOption productOptionQuery = QProductOption.productOption;

        // 필터링 조건에 따른 쿼리 작성
        var query = queryFactory.selectFrom(productQuery)
                .leftJoin(productQuery.productOptions, productOptionQuery)
                .where(
                        applyBrandFilter(productRequestDto.getBrands()),
                        applyColorFilter(productRequestDto.getColors()),
                        applySizeFilter(productRequestDto.getSizes()),
                        applyCategoryFilter(productRequestDto.getCategory(), productRequestDto.getDepth()),
                        applyPriceFilter(productRequestDto.getMinimumPrice(), productRequestDto.getMaximumPrice())
                );

        List<Product> products = query.fetch();

        return products.stream()
                .map(product -> ProductResponseDto.builder()
                        .productUuid(product.getProductUuid())
                        .brandUuid(product.getBrandUuid())
                        .name(product.getProductName())
                        .createdAt(product.getCreatedAt())
                        .updatedAt(product.getUpdatedAt())
                        .productOptions(
                                product.getProductOptions().stream()
                                        .map(option -> ProductOptionResponseDto.builder()
                                                .productOptionUuid(option.getProductOptionUuid())
                                                .productId(option.getProduct().getId())
                                                .sizeId(option.getSizeId())
                                                .colorId(option.getColorId())
                                                .etcOptionId(option.getEtcOptionId())
                                                .saleStatus(option.getSaleStatus().name())
                                                .price(option.getPrice())
                                                .discountRate(option.getDiscountRate())
                                                .discountPrice(option.getDiscountPrice())
                                                .build()
                                        ).toList()
                        )
                        .build()
                )
                .toList();
    }

    private BooleanExpression applyBrandFilter(List<String> brands) {
        if (brands == null || brands.isEmpty()) {
            return null;
        }
        List<String> brandIds = brandRepository.findByNameIn(brands).stream()
                .map(Brand::getBrandUuid).toList();
        return QProduct.product.brandUuid.in(brandIds);
    }

    private BooleanExpression applyColorFilter(List<String> colors) {
        if (colors == null || colors.isEmpty()) {
            return null;
        }
        List<Long> colorIds = colorRepository.findByNameIn(colors).stream()
                .map(color -> color.getId()).toList();
        return QProductOption.productOption.colorId.in(colorIds);
    }

    private BooleanExpression applySizeFilter(List<String> sizes) {
        if (sizes == null || sizes.isEmpty()) {
            return null;
        }
        List<Long> sizeIds = sizeRepository.findByNameIn(sizes).stream()
                .map(size -> size.getId()).toList();
        return QProductOption.productOption.sizeId.in(sizeIds);
    }

    private BooleanExpression applyCategoryFilter(String category, int depth) {
        if (category == null) {
            return null;
        }
        // 카테고리 필터 로직 작성 필요 (예시: category UUID가 특정 값과 일치하는지 확인)
        return null; // 실제 로직에 따라 수정해야 함
    }

    private BooleanExpression applyPriceFilter(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }
        BooleanExpression predicate = null;
        if (minPrice != null) {
            predicate = QProductOption.productOption.price.goe(minPrice);
        }
        if (maxPrice != null) {
            predicate = predicate != null
                    ? predicate.and(QProductOption.productOption.price.loe(maxPrice))
                    : QProductOption.productOption.price.loe(maxPrice);
        }
        return predicate;
    }
}
