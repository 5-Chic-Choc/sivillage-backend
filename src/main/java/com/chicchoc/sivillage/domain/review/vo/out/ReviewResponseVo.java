package com.chicchoc.sivillage.domain.review.vo.out;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseVo {

    private Long id;
    private Long productId;
    private String size;
    private String info;
    private int rate;
    private String content;
    private Date createAt;
}