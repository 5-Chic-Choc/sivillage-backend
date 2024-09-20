package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.QBrand;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.domain.QCategory;
import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
import com.chicchoc.sivillage.domain.product.domain.*;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findFilteredProducts(ProductRequestDto dto,
                                              int offset,
                                              int perPage,
                                              OrderSpecifier<?> orderSpecifier) {
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;
        final QProductCategory productCategory = QProductCategory.productCategory;

        BooleanBuilder predicate = createPredicate(dto);

        return queryFactory.selectFrom(product)
                .leftJoin(productOption)
                .on(product.id.eq(productOption.product.id))
                .leftJoin(productCategory)
                .on(product.id.eq(productCategory.productId))
                .where(predicate)
                .groupBy(product.id, productOption.price, productOption.discountRate)
                .offset(offset)
                .limit(perPage)
                .orderBy(orderSpecifier)
                .fetch();
    }

    private BooleanBuilder createPredicate(ProductRequestDto dto) {
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;
        BooleanBuilder predicate = new BooleanBuilder(); // BooleanBuilder 사용

        if (dto.getCategories() != null) {
            Long categoryId = findCategoryIdFromPath(dto.getCategories());
            if (categoryId == null) {
                throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
            }
            predicate.and(productCategoryFilter(categoryId));
        }

        // 사이즈 필터링
        if (dto.getSizes() != null && !dto.getSizes().isEmpty()) {
            List<Long> sizeIds = queryFactory.select(QSize.size.id)
                    .from(QSize.size)
                    .where(QSize.size.name.in(dto.getSizes()))
                    .fetch();

            if (sizeIds.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
            }

            predicate.and(productOption.sizeId.in(sizeIds));
        }

        // 색상 필터링
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            List<Long> colorIds = queryFactory.select(QColor.color.id)
                    .from(QColor.color)
                    .where(QColor.color.name.in(dto.getColors()))
                    .fetch();

            if (colorIds.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
            }

            predicate.and(productOption.colorId.in(colorIds));
        }

        // 브랜드 필터링
        if (dto.getBrands() != null && !dto.getBrands().isEmpty()) {
            List<String> brandUuids = queryFactory.select(QBrand.brand.brandUuid)
                    .from(QBrand.brand)
                    .where(QBrand.brand.name.in(dto.getBrands()))
                    .fetch();

            if (brandUuids.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_BRAND);
            }

            predicate.and(product.brandUuid.in(brandUuids));
        }

        // 가격 필터링
        if (dto.getMinimumPrice() != null) {
            predicate.and(productOption.price.goe(dto.getMinimumPrice()));
        }

        if (dto.getMaximumPrice() != null) {
            predicate.and(productOption.price.loe(dto.getMaximumPrice()));
        }

        if (!predicate.hasValue()) {
            throw new BaseException(BaseResponseStatus.INVALID_FILTER_CRITERIA);
        }

        return predicate;
    }

    private Long findCategoryIdFromPath(List<String> categories) {
        QCategory qcategory = QCategory.category;
        Category parentCategory = null;

        for (String categoryName : categories) {
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

    private BooleanBuilder productCategoryFilter(Long categoryId) {
        QProductCategory productCategory = QProductCategory.productCategory;
        if (categoryId == null) {
            throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
        }
        return new BooleanBuilder().and(productCategory.categoryId.eq(categoryId));
    }
}
