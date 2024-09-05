package com.chicchoc.sivillage.domain.review.dto.out;

import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Builder;
import java.util.Date;

public class ReviewResponseDto {

    private Long id;
    private Long productId;
    private String size;
    private String info;
    private int rate;
    private String content;
    private LocalDateTime createAt;

    public ReviewResponseVo toResponseVo() {
        return ReviewResponseVo.builder()
                .id(id)
                .productId(productId)
                .size(size)
                .info(info)
                .rate(rate)
                .content(content)
                .createAt(createAt).
                build();
    }

    @Builder
    public ReviewResponseDto(Long Id, Long productId, String size, String info, int rate, String content,
            LocalDateTime createAt) {
        this.id = Id;
        this.productId = productId;
        this.size = size;
        this.info = info;
        this.rate = rate;
        this.content = content;
        this.createAt = createAt;
    }
}