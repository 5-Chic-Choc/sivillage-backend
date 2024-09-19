package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.brand.domain.QBrand;
import com.chicchoc.sivillage.domain.category.domain.QCategory;
import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
import com.chicchoc.sivillage.domain.product.domain.QProduct;
import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotion;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotionBenefit;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotionProduct;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PromotionRepositoryImpl implements PromotionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Promotion> findFilteredPromotions(PromotionFilterRequestDto dto, int offset) {
        QPromotion promotion = QPromotion.promotion;
        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
        QPromotionProduct promotionProduct = QPromotionProduct.promotionProduct;
        QProduct product = QProduct.product; // Product 엔티티
        QBrand brand = QBrand.brand; // Brand 엔티티
        QCategory category = QCategory.category; // Category 엔티티
        QProductCategory productCategory = QProductCategory.productCategory; // ProductCategory 엔티티

        BooleanExpression predicate = createPredicate(dto);

        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;

        // PromotionProduct를 통해 Product를 조인하고, Product와 Brand, Category를 추가로 조인
        return queryFactory.selectFrom(promotion)
                .leftJoin(promotionProduct)
                .on(promotion.promotionUuid.eq(promotionProduct.promotion.promotionUuid))  // PromotionProduct 조인
                .leftJoin(product)
                .on(promotionProduct.productUuid.eq(product.productUuid))  // Product 조인
                .leftJoin(productCategory)
                .on(product.id.eq(productCategory.productId))  // Product와 ProductCategory 조인
                .leftJoin(category)
                .on(productCategory.categoryId.eq(category.id))  // ProductCategory와 Category 조인
                .leftJoin(brand)
                .on(product.brandUuid.eq(brand.brandUuid))  // Product의 Brand 조인
                .leftJoin(promotionBenefit)
                .on(promotion.promotionUuid.eq(promotionBenefit.promotionUuid))  // Promotion과 PromotionBenefit 조인
                .where(predicate)
                .offset(offset)
                .limit(perPage)
                .fetch();

    }

    private BooleanExpression createPredicate(PromotionFilterRequestDto dto) {
        QPromotion promotion = QPromotion.promotion;
        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
        QCategory category = QCategory.category;
        QBrand brand = QBrand.brand;

        BooleanExpression predicate = promotion.isNotNull(); // 기본 필터링

        // 카테고리 필터링
        if (dto.getCategoryId() != null) {
            predicate = predicate.and(category.id.eq(dto.getCategoryId()));  // Product의 Category로 필터링
        }

        // PromotionBenefit 필터링
        if (dto.getBenefitTypes() != null && !dto.getBenefitTypes().isEmpty()) {
            BooleanExpression benefitPredicate = promotionBenefit.benefitContent.in(dto.getBenefitTypes());
            predicate = predicate.and(benefitPredicate);
        }

        // 브랜드 필터링
        if (dto.getBrandUuids() != null && !dto.getBrandUuids().isEmpty()) {
            BooleanExpression brandPredicate = brand.brandUuid.in(dto.getBrandUuids());
            predicate = predicate.and(brandPredicate);
        }

        return predicate;
    }
}
