package com.chicchoc.sivillage.domain.review.vo.in;

import lombok.Getter;

@Getter
public class ReviewRequestVo {
  private Long productId;
  private String size;
  private String reviewOption;
  private String info;
  private int rate;
  private String content;
}
