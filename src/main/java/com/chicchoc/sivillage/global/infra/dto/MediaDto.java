package com.chicchoc.sivillage.global.infra.dto;

import com.chicchoc.sivillage.domain.media.domain.Media;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MediaDto {
    private String mediaUrl;
    private String mediaType;
    private String description;

    public Media toEntity(){
        return Media.builder()
                .mediaUrl(mediaUrl)
                .mediaType(mediaType)
                .description(description)
                .build();
    }
}
