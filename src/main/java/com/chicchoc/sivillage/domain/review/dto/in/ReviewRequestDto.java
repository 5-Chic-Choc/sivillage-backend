package com.chicchoc.sivillage.domain.review.dto.in;

import com.chicchoc.sivillage.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

    private final Long productId;
    private final String size;
    private final String reviewOption;
    private final String info;
    private final int rate;
    private final String content;


    public Review toEntity(Long userId) {
        return Review.builder()
                .userId(userId)
                .productId(productId)
                .size(size)
                .reviewOption(reviewOption)
                .info(info)
                .content(content)
                .build();
    }

    @Builder
    public ReviewRequestDto(Long productId, String size, String reviewOption, String info, int rate,
            String content) {
        this.productId = productId;
        this.size = size;
        this.reviewOption = reviewOption;
        this.info = info;
        this.rate = rate;
        this.content = content;
    }
}
