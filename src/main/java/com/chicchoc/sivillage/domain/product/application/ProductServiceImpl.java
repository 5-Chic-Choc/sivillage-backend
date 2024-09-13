package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
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
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    // QueryDSL을 사용하는 쿼리문을 위한 객체
    private final JPAQueryFactory queryFactory;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto dto) {
        // Q타입 객체를 메소드 상단으로 이동
        final QProduct product = QProduct.product;
        final QProductOption productOption = QProductOption.productOption;
        final QProductCategory productCategory = QProductCategory.productCategory;

        // 필터링 조건 생성
        BooleanExpression predicate = createPredicate(dto);

        // 페이징 처리
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
                .where(predicate) // 필터링 조건 쿼리에 적용
                .groupBy(product.id) // product_id로 그룹화
                .offset(offset) // 페이징 처리 시작 위치
                .limit(perPage) // 페이지 당 결과 개수
                .orderBy(getOrderSpecifier(dto.getSortBy(), dto.isAscending())) // 정렬
                .fetch(); // 실행 결과를 가져옴

        log.info("product : {}", products);

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

        // 카테고리 필터링
        if (dto.getCategories() != null) {
            Long categoryId = findCategoryIdFromPath(dto.getCategories());
            BooleanExpression categoryPredicate = productCategoryFilter(categoryId);
            predicate = predicate != null ? predicate.and(categoryPredicate) : categoryPredicate;
        }

        // 사이즈 필터링
        if (dto.getSizes() != null && !dto.getSizes().isEmpty()) {
            BooleanExpression sizePredicate = productOption.sizeId.in(
                    queryFactory.select(QSize.size.id)
                            .from(QSize.size)
                            .where(QSize.size.name.in(dto.getSizes()))
            );
            predicate = predicate != null ? predicate.and(sizePredicate) : sizePredicate;
        }

        // 색상 필터링
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            BooleanExpression colorPredicate = productOption.colorId.in(
                    queryFactory.selectFrom(QColor.color)
                            .where(QColor.color.name.in(dto.getColors()))
                            .fetch()
                            .stream()
                            .map(Color::getId)
                            .toList()
            );
            predicate = predicate != null ? predicate.and(colorPredicate) : colorPredicate;
        }

        // 브랜드 필터링
        if (dto.getBrands() != null && !dto.getBrands().isEmpty()) {
            BooleanExpression brandPredicate = product.brandUuid.in(
                    queryFactory.selectFrom(QBrand.brand)
                            .where(QBrand.brand.name.in(dto.getBrands()))
                            .fetch()
                            .stream()
                            .map(Brand::getBrandUuid)
                            .toList()
            );
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

        log.info("predicate = " + predicate);

        return predicate;
    }

    // 전체 경로를 사용한 카테고리 ID 찾기
    private Long findCategoryIdFromPath(List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("Category list cannot be null or empty");
        }

        // 최초의 부모 카테고리
        Category parentCategory = null;

        for (String categoryName : categories) {
            // 현재 카테고리 이름으로 카테고리 조회
            QCategory qcategory = QCategory.category;
            Category currentCategory = queryFactory
                    .selectFrom(qcategory)
                    .where(qcategory.name.eq(categoryName))
                    .fetchOne();

            // 현재 카테고리가 null이면 예외 처리
            if (currentCategory == null) {
                throw new IllegalStateException("Category not found: " + categoryName);
            }

            // 첫 번째 루프에서 parentCategory는 null이므로, root 카테고리 설정
            if (parentCategory == null) {
                parentCategory = currentCategory;
            } else {
                // 부모 카테고리의 자식 목록에서 현재 카테고리 찾기
                if (!parentCategory.getChild().contains(currentCategory)) {
                    throw new IllegalStateException("Category not found in parent category: " + categoryName);
                }

                // 현재 카테고리를 다음 부모로 설정
                parentCategory = currentCategory;
            }
        }

        // 마지막 카테고리의 ID 반환
        return parentCategory.getId();
    }


    //    private List<Long> findAllSubCategoryIds(Long categoryId) {
    //        if (categoryId == null) {
    //            return List.of();
    //        }
    //
    //        // 최상위 카테고리 ID를 포함한 모든 하위 카테고리 ID를 찾기 위해 사용
    //        List<Long> allCategoryIds = new ArrayList<>();
    //        findSubCategoriesRecursive(categoryId, allCategoryIds);
    //        return allCategoryIds;
    //    }
    //
    //    private void findSubCategoriesRecursive(Long categoryId, List<Long> allCategoryIds) {
    //        QCategory qcategory = QCategory.category;
    //
    //        // 현재 카테고리 ID를 결과 리스트에 추가
    //        allCategoryIds.add(categoryId);
    //
    //        // 현재 카테고리의 하위 카테고리 ID를 조회
    //        List<Long> childCategoryIds = queryFactory
    //                .select(qcategory.id)
    //                .from(qcategory)
    //                .where(qcategory.parent.id.eq(categoryId))
    //                .fetch();
    //
    //        // 하위 카테고리 ID를 결과 리스트에 추가
    //        allCategoryIds.addAll(childCategoryIds);
    //
    //        // 재귀적으로 하위 카테고리의 하위 카테고리도 찾기
    //        for (Long childCategoryId : childCategoryIds) {
    //            findSubCategoriesRecursive(childCategoryId, allCategoryIds);
    //        }
    //    }


    private BooleanExpression productCategoryFilter(Long categoryId) {
        QProductCategory productCategory = QProductCategory.productCategory;

        if (categoryId == null) {
            return null;
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
            default:
                return isAscending ? product.createdAt.asc() : product.createdAt.desc();
        }
    }

    @Override
    public Long findProductIdByUuid(String productUuid) {
        Product product = productRepository.findByProductUuid(productUuid)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return product.getId();
    }

    @Override
    public List<ProductOptionResponseDto> getProductOptions(Long productId) {
        List<ProductOption> productOptions = productOptionRepository.findByProductId(productId);
        return productOptions.stream()
                .map(ProductOptionResponseDto::fromEntity)
                .toList();
    }
}
