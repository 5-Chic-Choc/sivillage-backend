package com.chicchoc.sivillage.domain.review.dto.out;

import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.Date;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private String reviewUuid;
    private String productUuid;
    private String userUuid;
    private String sizeName;
    private String colorValue;
    private String reviewContent;
    private byte starpoint;
    private Integer likedCnt;
    private String reviewerEmail;

    public ReviewResponseVo toResponseVo() {
        return ReviewResponseVo.builder()
                .reviewUuid(reviewUuid)
                .productUuid(productUuid)
                .userUuid(userUuid)
                .sizeName(sizeName)
                .colorValue(colorValue)
                .reviewContent(reviewContent)
                .starpoint(starpoint)
                .likedCnt(likedCnt)
                .reviewerEmail(reviewerEmail)
                .build();
    }
}