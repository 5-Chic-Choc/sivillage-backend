package com.chicchoc.sivillage.domain.media.application;

import com.chicchoc.sivillage.domain.media.dto.out.MediaResponseDto;

public interface MediaService {
    MediaResponseDto getMediaById(Long mediaId);
}