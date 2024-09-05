package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.QCategoryProduct;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.QProduct;
import com.chicchoc.sivillage.domain.product.domain.QProductOrderOption;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findFilteredProducts(
            List<Long> categoryIds,
            List<Long> sizeIds,
            List<Long> colorIds,
            List<Long> brandIds,
            Integer minPrice,
            Integer maxPrice,
            String sortBy,
            boolean isAscending,
            int page,
            int perPage) {

        QProduct product = QProduct.product;
        QProductOrderOption productOrderOption = QProductOrderOption.productOrderOption;

        BooleanBuilder builder = new BooleanBuilder();

        // 브랜드 필터링
        if (!CollectionUtils.isEmpty(brandIds)) {
            builder.and(product.brandId.in(brandIds));
        }

        // 가격 필터링
        if (minPrice != null) {
            builder.and(productOrderOption.price.goe(minPrice));
        }

        if (maxPrice != null) {
            builder.and(productOrderOption.price.loe(maxPrice));
        }

        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;

        // 카테고리 필터링
        if (!CollectionUtils.isEmpty(categoryIds)) {
            builder.and(product.id.in(
                    queryFactory.select(categoryProduct.product.id)
                            .from(categoryProduct)
                            .where(categoryProduct.category.id.in(categoryIds))
            ));
        }

        // 색상 필터링
        if (!CollectionUtils.isEmpty(colorIds)) {
            builder.and(productOrderOption.color.id.in(colorIds));
        }

        // 사이즈 필터링
        if (!CollectionUtils.isEmpty(sizeIds)) {
            builder.and(productOrderOption.size.id.in(sizeIds));
        }

        // 동적 정렬
        PathBuilder<Product> entityPath = new PathBuilder<>(Product.class, "product");

        var query = queryFactory.selectFrom(product)
                .join(product.productOrderOptions, productOrderOption)
                .where(builder)
                .orderBy(isAscending ? entityPath.getString(sortBy).asc() : entityPath.getString(sortBy).desc())
                .offset((page - 1) * perPage)
                .limit(perPage);

        return query.fetch();
    }
}
