package com.chicchoc.sivillage.global.data.dto.review;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.data.dto.product.ProductDataRequestDto.ImageDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDataRequestDto {

    private String productCode;
    private String productEvalNo;
    private String reviewerName;
    private Integer likedCnt;

    @JsonProperty("starpoint")
    private Byte starPoint;
    private String reviewText;
    private String createdAt;
    private String reviewerInfo;
    private String optionInfo;

    @JsonProperty("review_rate_type_1")
    private String reviewRateType1;
    @JsonProperty("review_rate_text_1")
    private String reviewRateText1;
    @JsonProperty("review_rate_type_2")
    private String reviewRateType2;
    @JsonProperty("review_rate_text_2")
    private String reviewRateText2;
    @JsonProperty("review_rate_type_3")
    private String reviewRateType3;
    @JsonProperty("review_rate_text_3")
    private String reviewRateText3;

    private List<ImageDto> images;

    public Review toEntity() {

        // optionInfo에 콜론(:)이 포함되어 있으면, 콜론을 기준으로 문자열을 나누어 뒷부분을 optionName에 저장
        // ex) optionInfo = “구매옵션 : 화이트/FR”

        return Review.builder()
                .reviewUuid(productEvalNo)
                .productUuid(productCode)
                .userUuid(new NanoIdGenerator().generateNanoId())
                .reviewerEmail(reviewerName.isEmpty() ? "anonymous" : reviewerName)
                .reviewContent(reviewText)
                .starPoint(starPoint != null ? starPoint : (byte) ThreadLocalRandom.current().nextInt(0, 6))
                .likedCnt(likedCnt)
                .sizeName(null)
                .colorValue(null)
                .optionName(parseOptionName(optionInfo))
                .reviewRateType1(reviewRateType1)
                .reviewRateText1(reviewRateText1)
                .reviewRateType2(reviewRateType2)
                .reviewRateText2(reviewRateText2)
                .reviewRateType3(reviewRateType3)
                .reviewRateText3(reviewRateText3)
                .build();
    }

    private String parseOptionName(String optionInfo) {

        if (optionInfo != null && optionInfo.contains(":")) {
            String[] parts = optionInfo.split(":");
            if (parts.length > 1) {
                return parts[1].trim();  // 공백을 제거
            }
        }
        return null;
    }
}
