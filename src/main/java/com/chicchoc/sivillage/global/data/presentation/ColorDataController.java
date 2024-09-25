package com.chicchoc.sivillage.global.data.presentation;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.data.application.ColorDataService;
import com.chicchoc.sivillage.global.data.dto.color.ProductColorRequestDto;
import com.chicchoc.sivillage.global.data.util.JsonFileUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "데이터", description = "데이터 삽입용 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/data")
public class ColorDataController {

    private final ColorDataService colorDataService;

    @Operation(summary = "상품 색상 데이터 업로드")
    @PostMapping(value = "/product/color", consumes = {"multipart/form-data"})
    public BaseResponse<Void> uploadProductColor(@RequestParam("file") MultipartFile file) throws IOException {

        List<ProductColorRequestDto> productColorRequestDtos = JsonFileUtil.parseFileToDtoList(
                file,
                ProductColorRequestDto[].class);

        // 변환된 DTO 리스트 저장
        colorDataService.saveColorData(productColorRequestDtos);

        return new BaseResponse<>();
    }
}
