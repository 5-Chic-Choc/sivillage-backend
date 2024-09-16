package com.chicchoc.sivillage.domain.media.presentation;

import com.chicchoc.sivillage.domain.media.application.MediaService;
import com.chicchoc.sivillage.domain.media.dto.out.MediaResponseDto;
import com.chicchoc.sivillage.domain.media.vo.out.MediaResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @Operation(summary = "getMedia API", description = "미디어 조회", tags = {"Media"})
    @GetMapping("/{mediaId}")
    public BaseResponse<MediaResponseVo> getMediaById(@PathVariable Long mediaId) {
        MediaResponseDto responseDto = mediaService.getMediaById(mediaId);

        MediaResponseVo responseVo = MediaResponseVo.builder()
                .id(responseDto.getId())
                .mediaUrl(responseDto.getMediaUrl())
                .mediaType(responseDto.getMediaType())
                .description(responseDto.getDescription())
                .build();

        return new BaseResponse<>(responseVo);
    }
}