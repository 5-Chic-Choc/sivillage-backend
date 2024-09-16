package com.chicchoc.sivillage.domain.media.application;

import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.dto.out.MediaResponseDto;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Override
    public MediaResponseDto getMediaById(Long mediaId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_MEDIA));

        return MediaResponseDto.fromEntity(media);
    }
}