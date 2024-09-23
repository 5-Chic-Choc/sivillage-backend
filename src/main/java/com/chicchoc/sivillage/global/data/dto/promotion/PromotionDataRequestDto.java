package com.chicchoc.sivillage.global.data.dto.promotion;

import com.chicchoc.sivillage.domain.promotion.domain.Promotion;
import com.chicchoc.sivillage.domain.promotion.domain.PromotionProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PromotionDataRequestDto {

    private String id; // promotionUuid
    private String type; // promotionType( 이벤트혜택, 브랜드기획전, 제휴혜택)
    private String thumbnailImg;
    private String brand; // 브랜드명
    private String title; // 프로모션 제목
    private String description; // 프로모션 설명
    private List<String> tags; // 태그들
    private List<String> promotionImages; // 프로모션 이미지들
    private CouponDto coupon; // 쿠폰 정보
    private List<CategoryProductDto> categoryProducts; // 카테고리별 상품 리스트(핵심)

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class CouponDto {

        private String name;
        private String discount;
        private String period;
        private String kind;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class CategoryProductDto {

        @JsonProperty("categoryName")
        private String categoryName;
        private List<ProductDto> products;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ProductDto {

        private String goodsNo;
        private String name;

        public PromotionProduct toPromotionProductEntity(String promotionUuid, String promotionType) {
            return PromotionProduct.builder()
                    .promotionUuid(promotionUuid)
                    .productUuid(goodsNo)
                    .promotionType(promotionType == null ? "" : promotionType)
                    .build();
        }
    }


    public Promotion toEntity() {
        return Promotion.builder()
                .promotionUuid(id)
                .title(title)
                .description(description)
                .thumbnailUrl(thumbnailImg)
                .build();
    }

    public List<PromotionProduct> toPromotionProductEntity(Promotion promotion) {
        return categoryProducts.stream() // 카테고리별로 순회
                .flatMap(categoryProductDto -> categoryProductDto.getProducts().stream()  // 각 카테고리의 상품들을 스트림으로 변환
                        .map(productDto -> PromotionProduct.builder()  // 상품을 PromotionProduct로 매핑
                                .promotion(promotion)
                                .promotionUuid(promotion.getPromotionUuid())
                                .productUuid(productDto.getGoodsNo())
                                // 각 카테고리의 categoryName을 promotionType으로 설정
                                .promotionType(categoryProductDto.getCategoryName())
                                .build())) // PromotionProduct 빌더 생성
                .collect(Collectors.toList());  // 결과를 리스트로 수집
    }
}

