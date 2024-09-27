package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.best.domain.QProductScore;
import com.chicchoc.sivillage.domain.brand.domain.QBrand;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.domain.QCategory;
import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
import com.chicchoc.sivillage.domain.product.domain.*;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.FilteredProductAttributesDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductCountAndPageDto;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QProduct product = QProduct.product;
    private static final QProductOption productOption = QProductOption.productOption;
    private static final QCategory qcategory = QCategory.category;
    private static final QProductCategory productCategory = QProductCategory.productCategory;
    private static final QProductHashtag productHashtag = QProductHashtag.productHashtag;
    private static final QProductScore productScore = QProductScore.productScore;
    private static final QColor qcolor = QColor.color;
    private static final QSize qsize = QSize.size;

    @Override
    public List<Product> findFilteredProducts(ProductRequestDto dto) {

        BooleanBuilder predicate = createPredicate(dto);

        int page = dto.getPage() != null ? dto.getPage() : 1;
        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
        int offset = (page - 1) * perPage;

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(dto.getSortBy(), dto.isAscending());

        return queryFactory.selectFrom(product)
                .leftJoin(productOption).on(product.id.eq(productOption.product.id))
                .where(predicate)
                .groupBy(product.id, productOption.price, productOption.discountRate)
                .offset(offset)
                .limit(perPage)
                .orderBy(orderSpecifier)
                .fetch();
    }
    //    @Override
    //    public List<Product> findFilteredProducts(ProductRequestDto dto) {
    //
    //        BooleanBuilder predicate = createPredicate(dto);
    //
    //        int page = dto.getPage() != null ? dto.getPage() : 1;
    //        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
    //        int offset = (page - 1) * perPage;
    //
    //        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(dto.getSortBy(), dto.isAscending());
    //
    //        // 모든 조인을 쿼리 내에서 처리
    //        return queryFactory.selectFrom(product)
    //                .leftJoin(productOption).on(product.id.eq(productOption.product.id))  // Product와 ProductOption 조인
    //                .leftJoin(QBrand.brand).on(product.brandUuid.eq(QBrand.brand.brandUuid))  // Product와 Brand 조인
    //                .leftJoin(productCategory).on(product.id.eq(productCategory.productId))  // Product와 ProductCategory 조인
    //                .leftJoin(qcategory).on(productCategory.categoryId.eq(qcategory.id))  // ProductCategory와 Category 조인
    //                .leftJoin(QColor.color).on(productOption.colorId.eq(QColor.color.id))  // ProductOption과 Color 조인
    //                .leftJoin(QSize.size).on(productOption.sizeId.eq(QSize.size.id))  // ProductOption과 Size 조인
    //                .where(predicate)
    //                .groupBy(product.id, productOption.price, productOption.discountRate)
    //                .offset(offset)
    //                .limit(perPage)
    //                .orderBy(orderSpecifier)
    //                .fetch();
    //    }
    //
    //    private BooleanBuilder createPredicate(ProductRequestDto dto) {
    //        BooleanBuilder predicate = new BooleanBuilder();
    //
    //        // 브랜드 필터링
    //        if (dto.getBrands() != null && !dto.getBrands().isEmpty()) {
    //            predicate.and(QBrand.brand.brandUuid.in(dto.getBrands()));
    //        }
    //
    //        // 카테고리 필터링
    //        if (dto.getCategories() != null && !dto.getCategories().isEmpty()) {
    //            predicate.and(qcategory.name.in(dto.getCategories()));
    //        }
    //
    //        // 색상 필터링
    //        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
    //            predicate.and(QColor.color.name.in(dto.getColors()));
    //        }
    //
    //        // 사이즈 필터링
    //        if (dto.getSizes() != null && !dto.getSizes().isEmpty()) {
    //            predicate.and(QSize.size.name.in(dto.getSizes()));
    //        }
    //
    //        return predicate;
    //    }


    @Override
    public ProductCountAndPageDto findFilteredProductsCount(ProductRequestDto dto) {

        BooleanBuilder predicate = createPredicate(dto);

        // 전체 개수 계산
        Long totalCount = queryFactory
                .select(product.countDistinct())
                .from(product)
                .leftJoin(productOption).on(product.id.eq(productOption.product.id))
                .where(predicate)
                .fetchOne();

        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
        int totalPages = (int) Math.ceil((double) totalCount / perPage);

        return ProductCountAndPageDto.builder()
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
    }


    private OrderSpecifier<?> getOrderSpecifier(String sortBy, boolean isAscending) {

        Map<String, OrderSpecifier<?>> orderMap = new HashMap<>();
        orderMap.put(
                "discount_rate", isAscending ? productOption.discountRate.asc() : productOption.discountRate.desc());
        orderMap.put("price", isAscending ? productOption.price.asc() : productOption.price.desc());
        orderMap.put("name", isAscending ? product.productName.asc() : product.productName.desc());
        orderMap.put("createdAt", isAscending ? product.createdAt.asc() : product.createdAt.desc());

        OrderSpecifier<?> orderSpecifier = orderMap.get(sortBy);

        if (orderSpecifier == null) {
            throw new BaseException(BaseResponseStatus.INVALID_SORT_BY_PARAMETER);
        }

        return orderSpecifier;
    }


    private BooleanBuilder createPredicate(ProductRequestDto dto) {

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
            List<Long> sizeIds = queryFactory.select(qsize.id)
                    .from(qsize)
                    .where(qsize.name.in(dto.getSizes()))
                    .fetch();

            if (sizeIds.isEmpty()) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
            }

            predicate.and(productOption.sizeId.in(sizeIds));
        }

        // 색상 필터링
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            List<Long> colorIds = queryFactory.select(qcolor.id)
                    .from(qcolor)
                    .where(qcolor.name.in(dto.getColors()))
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

        // 키워드 검색 추가
        if (dto.getKeywords() != null && !dto.getKeywords().isEmpty()) {
            BooleanBuilder keywordPredicate = new BooleanBuilder();

            // 제품 이름에서 검색
            keywordPredicate.or(product.productName.containsIgnoreCase(dto.getKeywords()));

            // 브랜드 이름에서 검색
            List<String> brandUuids = queryFactory.select(QBrand.brand.brandUuid)
                    .from(QBrand.brand)
                    .where(QBrand.brand.name.containsIgnoreCase(dto.getKeywords()))
                    .fetch();

            if (!brandUuids.isEmpty()) {
                keywordPredicate.or(product.brandUuid.in(brandUuids));
            }

            // 해시태그에서 검색
            List<String> productUuids = queryFactory.select(productHashtag.productUuid)
                    .from(productHashtag)
                    .where(productHashtag.hashtagContent.containsIgnoreCase(dto.getKeywords()))
                    .fetch();

            if (!productUuids.isEmpty()) {
                keywordPredicate.or(product.productUuid.in(productUuids));
            }

            predicate.and(keywordPredicate);
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

        Category parentCategory = null;

        for (String categoryName : categories) {
            Category currentCategory = queryFactory
                    .selectFrom(qcategory)
                    .where(qcategory.name.eq(categoryName)
                            .and(parentCategory == null ? qcategory.parent.isNull()
                                    : qcategory.parent.eq(parentCategory)))
                    .fetchFirst();

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

        if (categoryId == null) {
            throw new BaseException(BaseResponseStatus.INVALID_CATEGORY_PATH);
        }

        // 카테고리와 제품이 연관된 테이블을 직접 서브쿼리로 필터링
        List<Long> productIds = queryFactory
                .select(productCategory.productId)
                .from(productCategory)
                .where(productCategory.categoryId.eq(categoryId))
                .fetch();

        // 서브쿼리 결과로 필터링
        return new BooleanBuilder().and(product.id.in(productIds));
    }

    @Override
    public List<Product> findTopBestProductsByCategory(ProductRequestDto dto) {

        BooleanBuilder predicate = new BooleanBuilder();

        int page = dto.getPage() != null ? dto.getPage() : 1;
        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 100;
        int offset = (page - 1) * perPage;

        // 카테고리 필터링
        if (dto.getCategories() != null && !dto.getCategories().isEmpty()) {
            List<Long> categoryIds = queryFactory.select(qcategory.id)
                    .from(qcategory)
                    .where(qcategory.name.in(dto.getCategories()))
                    .fetch();
            if (!categoryIds.isEmpty()) {
                predicate.and(productCategory.categoryId.in(categoryIds));
            }
        }

        return queryFactory.selectFrom(product)
                .leftJoin(productCategory).on(product.id.eq(productCategory.productId))
                .leftJoin(productScore).on(product.productUuid.eq(productScore.productUuid)) // 스코어 조인
                .where(predicate)
                .orderBy(productScore.totalScore.desc()) // totalScore 내림차순
                .offset(offset)
                .limit(perPage)
                .fetch();
    }

    public FilteredProductAttributesDto findFilteredProductAttributes(ProductRequestDto dto) {
        BooleanBuilder predicate = createPredicate(dto);

        List<String> colorNames = queryFactory
                .select(qcolor.name)
                .from(productOption)
                .leftJoin(qcolor).on(productOption.colorId.eq(QColor.color.id))
                .where(predicate)
                .distinct()
                .fetch();

        List<String> sizeNames = queryFactory
                .select(qsize.name)
                .from(productOption)
                .leftJoin(qsize).on(productOption.sizeId.eq(qsize.id))
                .where(predicate)
                .distinct()
                .fetch();

        List<String> brandNames = queryFactory
                .select(QBrand.brand.name)
                .from(product)
                .leftJoin(QBrand.brand).on(product.brandUuid.eq(QBrand.brand.brandUuid))
                .where(predicate)
                .distinct()
                .fetch();

        return FilteredProductAttributesDto.builder()
                .colorIds(colorNames)
                .sizeIds(sizeNames)
                .brandUuids(brandNames)
                .build();
    }
}
