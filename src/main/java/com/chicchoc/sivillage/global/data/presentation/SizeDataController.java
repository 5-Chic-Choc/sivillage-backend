package com.chicchoc.sivillage.global.data.presentation;

import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.data.application.ColorDataService;
import com.chicchoc.sivillage.global.data.application.SizeDataService;
import com.chicchoc.sivillage.global.data.dto.color.ProductColorRequestDto;
import com.chicchoc.sivillage.global.data.dto.size.ProductSizeRequestDto;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/data")
public class SizeDataController {

    private final SizeDataService sizeDataService;

    @Operation(summary = "상품 사이즈 데이터 업로드")
    @PostMapping(value = "/product/size", consumes = {"multipart/form-data"})
    public BaseResponse<Void> uploadProductSize(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new BaseException(BaseResponseStatus.ILLEGAL_ARGUMENT);
        }

        try {
            // 파일을 String으로 변환
            String content = new String(file.getBytes());

            // JSON을 List<DTO>로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductSizeRequestDto> productSizeRequestDtos = objectMapper.readValue(content,
                    new TypeReference<List<ProductSizeRequestDto>>() {
                    });

            sizeDataService.saveSizeData(productSizeRequestDtos);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new BaseException(BaseResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new BaseResponse<>();
    }
}
