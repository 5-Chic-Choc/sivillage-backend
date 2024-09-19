package com.chicchoc.sivillage.domain.review.dto.in;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.domain.ReviewMedia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMediaRequestDto {
    private Review review;
    private Long mediaId;
    private Integer mediaOrder;

    public ReviewMedia toEntity(){
        return ReviewMedia.builder()
                .review(review)
                .mediaId(mediaId)
                .mediaOrder(mediaOrder)
                .build();
    }
}
