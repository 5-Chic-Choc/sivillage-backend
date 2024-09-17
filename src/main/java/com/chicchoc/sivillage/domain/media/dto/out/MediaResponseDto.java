package com.chicchoc.sivillage.domain.media.dto.out;

import com.chicchoc.sivillage.domain.media.domain.Media;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MediaResponseDto {
    private Long id;
    private String mediaUrl;
    private String mediaType;
    private String description;

    public static MediaResponseDto fromEntity(Media media) {
        return MediaResponseDto.builder()
                .id(media.getId())
                .mediaUrl(media.getMediaUrl())
                .mediaType(media.getMediaType())
                .description(media.getDescription())
                .build();
    }
}