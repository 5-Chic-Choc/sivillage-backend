package com.chicchoc.sivillage.domain.media.vo.out;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaResponseVo {
    private Long id;
    private String mediaUrl;
    private String mediaType;
    private String description;
}