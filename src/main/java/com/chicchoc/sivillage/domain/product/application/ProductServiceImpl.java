package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.QBrand;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.domain.QCategory;
import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
import com.chicchoc.sivillage.domain.product.domain.*;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductOptionResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final JPAQueryFactory queryFactory;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto dto) {
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;
        final QProductCategory productCategory = QProductCategory.productCategory;

        BooleanExpression predicate = createPredicate(dto);

        final int page = dto.getPage() != null ? dto.getPage() : 1;
        final int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
        final int offset = (page - 1) * perPage;

        log.info("page : {}", page);
        log.info("perPage : {}", perPage);
        log.info("offset : {}", offset);

        List<Product> products = queryFactory.selectFrom(product)
                .leftJoin(productOption)
                .on(product.id.eq(productOption.product.id))
                .leftJoin(productCategory)
                .on(product.id.eq(productCategory.productId))
                .where(predicate)
                .groupBy(product.id, productOption.price, productOption.discountRate)
                .offset(offset)
                .limit(perPage)
                .orderBy(getOrderSpecifier(dto.getSortBy(), dto.isAscending()))
                .fetch();

        // 필터링 결과가 없을 때 빈 리스트를 반환
        if (products.isEmpty()) {
            return Collections.emptyList();
        }

        return products.stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }


    private BooleanExpression createPredicate(ProductRequestDto dto) {
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;

        BooleanExpression predicate = null;

        log.info("cate : {}", dto.getCategories());
        log.info("dep : {}", dto.getDepth());

        if (dto.getCategories() != null) {
            Long categoryId = findCategoryIdFromPath(dto.getCategories());
            if (categoryId == null) {
                throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
            }
            BooleanExpression categoryPredicate = productCategoryFilter(categoryId);
            predicate = predicate != null ? predicate.and(categoryPredicate) : categoryPredicate;
        }

        // 사이즈 필터링
        if (dto.getSizes() != null && !dto.getSizes().isEmpty()) {
            List<Long> sizeIds = queryFactory.select(QSize.size.id)
                    .from(QSize.size)
                    .where(QSize.size.name.in(dto.getSizes()))
                    .fetch();

            if (sizeIds.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION); // 새로운 에러 코드로 대체 가능
            }

            BooleanExpression sizePredicate = productOption.sizeId.in(sizeIds);
            predicate = predicate != null ? predicate.and(sizePredicate) : sizePredicate;
        }

        // 색상 필터링
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            List<Long> colorIds = queryFactory.select(QColor.color.id)
                    .from(QColor.color)
                    .where(QColor.color.name.in(dto.getColors()))
                    .fetch();

            if (colorIds.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION); // 새로운 에러 코드로 대체 가능
            }

            BooleanExpression colorPredicate = productOption.colorId.in(colorIds);
            predicate = predicate != null ? predicate.and(colorPredicate) : colorPredicate;
        }

        // 브랜드 필터링
        if (dto.getBrands() != null && !dto.getBrands().isEmpty()) {
            List<String> brandUuids = queryFactory.select(QBrand.brand.brandUuid)
                    .from(QBrand.brand)
                    .where(QBrand.brand.name.in(dto.getBrands()))
                    .fetch();

            if (brandUuids.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_BRAND); // 새로운 에러 코드로 대체 가능
            }

            BooleanExpression brandPredicate = product.brandUuid.in(brandUuids);
            predicate = predicate != null ? predicate.and(brandPredicate) : brandPredicate;
        }

        // 가격 필터링
        if (dto.getMinimumPrice() != null) {
            predicate = predicate != null ? predicate.and(productOption.price.goe(dto.getMinimumPrice()))
                    : productOption.price.goe(dto.getMinimumPrice());
        }

        if (dto.getMaximumPrice() != null) {
            predicate = predicate != null ? predicate.and(productOption.price.loe(dto.getMaximumPrice()))
                    : productOption.price.loe(dto.getMaximumPrice());
        }

        if (predicate == null) {
            throw new BaseException(BaseResponseStatus.INVALID_FILTER_CRITERIA);
        }

        return predicate;
    }


    private Long findCategoryIdFromPath(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
        }

        Category parentCategory = null;

        for (String categoryName : categories) {
            QCategory qcategory = QCategory.category;
            Category currentCategory = queryFactory
                    .selectFrom(qcategory)
                    .where(qcategory.name.eq(categoryName))
                    .fetchOne();

            if (currentCategory == null) {
                throw new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND);
            }

            if (parentCategory == null) {
                parentCategory = currentCategory;
            } else {
                if (!parentCategory.getChild().contains(currentCategory)) {
                    throw new BaseException(BaseResponseStatus.CATEGORY_NOT_FOUND);
                }
                parentCategory = currentCategory;
            }
        }

        return parentCategory.getId();
    }

    private BooleanExpression productCategoryFilter(Long categoryId) {
        QProductCategory productCategory = QProductCategory.productCategory;

        if (categoryId == null) {
            throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
        }

        return productCategory.categoryId.eq(categoryId);
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
}
