package com.chicchoc.sivillage.domain.review.dto.out;

import com.chicchoc.sivillage.domain.review.domain.ReviewMedia;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewMediaResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewMediaResponseDto {

    private String reviewUuid;
    private Long mediaId;
    private int mediaOrder;

    public ReviewMediaResponseVo toVo() {
        return ReviewMediaResponseVo.builder()
                .reviewUuid(reviewUuid)
                .mediaId(mediaId)
                .mediaOrder(mediaOrder)
                .build();
    }

    public static ReviewMediaResponseDto fromEntity(ReviewMedia reviewMedia) {
        return ReviewMediaResponseDto.builder()
                .reviewUuid(reviewMedia.getReviewUuid())
                .mediaId(reviewMedia.getMediaId())
                .mediaOrder(reviewMedia.getMediaOrder())
                .build();
    }
}
