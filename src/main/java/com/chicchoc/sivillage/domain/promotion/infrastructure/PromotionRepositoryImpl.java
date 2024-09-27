//package com.chicchoc.sivillage.domain.promotion.infrastructure;
//
//import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
//import com.chicchoc.sivillage.domain.product.domain.QProduct;
//import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotion;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotionBenefit;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotionProduct;
//import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class PromotionRepositoryImpl implements PromotionRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Promotion> findFilteredPromotions(PromotionFilterRequestDto dto, int offset) {
//        QPromotion promotion = QPromotion.promotion;
//
//        BooleanBuilder predicate = createPredicate(dto);
//
//        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
//
//        return queryFactory.selectFrom(promotion)
//                .where(predicate)
//                .offset(offset)
//                .limit(perPage)
//                .fetch();
//    }
//
//    private BooleanBuilder createPredicate(PromotionFilterRequestDto dto) {
//        QPromotion promotion = QPromotion.promotion;
//        QPromotionProduct promotionProduct = QPromotionProduct.promotionProduct;
//        QProduct product = QProduct.product;
//        QProductCategory productCategory = QProductCategory.productCategory;
//        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
//
//        BooleanBuilder predicate = new BooleanBuilder();
//
//        // 카테고리 필터링
//        if (dto.getCategoryId() != null) {
//            List<Long> productIds = queryFactory
//                    .select(productCategory.productId)
//                    .from(productCategory)
//                    .where(productCategory.categoryId.eq(dto.getCategoryId()))
//                    .fetch();
//
//            // productIds에 해당하는 productUuid 목록을 미리 조회
//            List<String> productUuids = queryFactory
//                    .select(product.productUuid)
//                    .from(product)
//                    .where(product.id.in(productIds))  // productId와 매칭
//                    .fetch();
//
//            // 조회된 productUuid 목록을 사용해 필터링
//            predicate.and(promotion.promotionUuid.in(
//                    queryFactory.select(promotionProduct.promotion.promotionUuid)
//                            .from(promotionProduct)
//                            .where(promotionProduct.productUuid.in(productUuids))  // productUuid와 매칭
//            ));
//        }
//
//
//        // PromotionBenefit 필터링
//        if (dto.getBenefitTypes() != null && !dto.getBenefitTypes().isEmpty()) {
//            List<String> promotionUuids = queryFactory
//                    .select(promotionBenefit.promotionUuid)
//                    .from(promotionBenefit)
//                    .where(promotionBenefit.benefitContent.in(dto.getBenefitTypes()))
//                    .fetch();
//
//            predicate.and(promotion.promotionUuid.in(promotionUuids));
//        }
//
//        // 브랜드 필터링
//        if (dto.getBrandUuids() != null && !dto.getBrandUuids().isEmpty()) {
//            List<String> productUuids = queryFactory
//                    .select(product.productUuid)
//                    .from(product)
//                    .where(product.brandUuid.in(dto.getBrandUuids()))
//                    .fetch();
//
//            predicate.and(promotion.promotionUuid.in(
//                    queryFactory.select(QPromotionProduct.promotionProduct.promotion.promotionUuid)
//                            .from(QPromotionProduct.promotionProduct)
//                            .where(QPromotionProduct.promotionProduct.productUuid.in(productUuids))
//            ));
//        }
//
//        return predicate;
//    }
//}

package com.chicchoc.sivillage.domain.promotion.infrastructure;

import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
import com.chicchoc.sivillage.domain.product.domain.QProduct;
import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotion;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotionBenefit;
import com.chicchoc.sivillage.domain.promotion.domain.QPromotionProduct;
import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;
import com.querydsl.core.BooleanBuilder;
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

        BooleanBuilder predicate = createPredicate(dto);

        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;

        return queryFactory.selectFrom(promotion)
                .where(predicate)
                .offset(offset)
                .limit(perPage)
                .fetch();
    }

    private BooleanBuilder createPredicate(PromotionFilterRequestDto dto) {
        QPromotion promotion = QPromotion.promotion;
        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
        QProduct product = QProduct.product;
        QProductCategory productCategory = QProductCategory.productCategory;
        QPromotionProduct promotionProduct = QPromotionProduct.promotionProduct;

        BooleanBuilder predicate = new BooleanBuilder();

        // 서브쿼리로 카테고리 필터링
        if (dto.getCategoryId() != null) {
            predicate.and(
                    promotion.promotionUuid.in(
                            queryFactory.select(promotionProduct.promotion.promotionUuid)
                                    .from(promotionProduct)
                                    .where(promotionProduct.productUuid.in(
                                            queryFactory.select(product.productUuid)
                                                    .from(product)
                                                    .where(product.id.in(
                                                            queryFactory.select(productCategory.productId)
                                                                    .from(productCategory)
                                                                    .where(productCategory
                                                                            .categoryId.eq(dto.getCategoryId()))
                                                    ))
                                    ))
                    )
            );
        }

        // 서브쿼리로 PromotionBenefit 필터링
        if (dto.getBenefitTypes() != null && !dto.getBenefitTypes().isEmpty()) {
            predicate.and(
                    promotion.promotionUuid.in(
                            queryFactory.select(promotionBenefit.promotionUuid)
                                    .from(promotionBenefit)
                                    .where(promotionBenefit.benefitContent.in(dto.getBenefitTypes()))
                    )
            );
        }

        // 서브쿼리로 브랜드 필터링
        if (dto.getBrandUuids() != null && !dto.getBrandUuids().isEmpty()) {
            predicate.and(
                    promotion.promotionUuid.in(
                            queryFactory.select(QPromotionProduct.promotionProduct.promotion.promotionUuid)
                                    .from(QPromotionProduct.promotionProduct)
                                    .where(QPromotionProduct.promotionProduct.productUuid.in(
                                            queryFactory.select(product.productUuid)
                                                    .from(product)
                                                    .where(product.brandUuid.in(dto.getBrandUuids()))
                                    ))
                    )
            );
        }

        return predicate;
    }
}
//package com.chicchoc.sivillage.domain.promotion.infrastructure;
//
//import com.chicchoc.sivillage.domain.brand.domain.QBrand;
//import com.chicchoc.sivillage.domain.category.domain.QCategory;
//import com.chicchoc.sivillage.domain.category.domain.QProductCategory;
//import com.chicchoc.sivillage.domain.product.domain.QProduct;
//import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotion;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotionBenefit;
//import com.chicchoc.sivillage.domain.promotion.domain.QPromotionProduct;
//import com.chicchoc.sivillage.domain.promotion.dto.in.PromotionFilterRequestDto;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class PromotionRepositoryImpl implements PromotionRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//
//    @Override
//    public List<Promotion> findFilteredPromotions(PromotionFilterRequestDto dto, int offset) {
//        QPromotion promotion = QPromotion.promotion;
//        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
//        QPromotionProduct promotionProduct = QPromotionProduct.promotionProduct;
//        QProduct product = QProduct.product;
//        QBrand brand = QBrand.brand;
//        QCategory category = QCategory.category;
//        QProductCategory productCategory = QProductCategory.productCategory;
//
//        BooleanBuilder predicate = createPredicate(dto);
//
//        int perPage = dto.getPerPage() != null ? dto.getPerPage() : 20;
//
//        return queryFactory.selectFrom(promotion)
//                .leftJoin(promotionProduct)
//                .fetchJoin()
//                .on(promotion.promotionUuid.eq(promotionProduct.promotion.promotionUuid))  // PromotionProduct 조인
//                .leftJoin(product)
//                .on(promotionProduct.productUuid.eq(product.productUuid))  // Product 조인
//                .leftJoin(productCategory)
//                .on(product.id.eq(productCategory.productId))  // Product와 ProductCategory 조인
//                .leftJoin(category)
//                .on(productCategory.categoryId.eq(category.id))  // ProductCategory와 Category 조인
//                .leftJoin(brand)
//                .on(product.brandUuid.eq(brand.brandUuid))  // Product의 Brand 조인
//                .leftJoin(promotionBenefit)
//                .on(promotion.promotionUuid.eq(promotionBenefit.promotionUuid))  // Promotion과 PromotionBenefit 조인
//                .where(predicate)
//                .groupBy(
//                        promotion.promotionUuid,
//                        promotion.id,
//                        promotion.description,
//                        promotion.thumbnailUrl,
//                        promotion.title
//                )
//                .offset(offset)
//                .limit(perPage)
//                .fetch();
//    }
//
//    private BooleanBuilder createPredicate(PromotionFilterRequestDto dto) {
//        QPromotionBenefit promotionBenefit = QPromotionBenefit.promotionBenefit;
//        QCategory category = QCategory.category;
//        QBrand brand = QBrand.brand;
//
//        BooleanBuilder predicate = new BooleanBuilder();
//
//        // 카테고리 필터링
//        if (dto.getCategoryId() != null) {
//            predicate.and(category.id.eq(dto.getCategoryId()));  // Product의 Category로 필터링
//        }
//
//        // PromotionBenefit 필터링
//        if (dto.getBenefitTypes() != null && !dto.getBenefitTypes().isEmpty()) {
//            predicate.and(promotionBenefit.benefitContent.in(dto.getBenefitTypes()));
//        }
//
//        // 브랜드 필터링
//        if (dto.getBrandUuids() != null && !dto.getBrandUuids().isEmpty()) {
//            predicate.and(brand.brandUuid.in(dto.getBrandUuids()));
//        }
//
//        return predicate;
//    }
//}