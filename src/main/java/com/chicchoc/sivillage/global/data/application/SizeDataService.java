package com.chicchoc.sivillage.global.data.application;

import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.data.dto.color.ColorType;
import com.chicchoc.sivillage.global.data.dto.color.ProductColorRequestDto;
import com.chicchoc.sivillage.global.data.dto.size.ProductSizeRequestDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SizeDataService {

    private final SizeRepository sizeRepository;

    //상품 사이즈 데이터 저장
    public void saveSizeData(List<ProductSizeRequestDto> sizeDtos) {

        for (ProductSizeRequestDto dto : sizeDtos) {
            String sizeValue = dto.getSizeValue();

            // DB에 이미 해당 사이즈가 있는지 확인
            Optional<Size> existingSize = sizeRepository.findByName(sizeValue);

            // 사이즈가 이미 존재하면 로그 처리, 없으면 새로 저장
            if (existingSize.isPresent()) {
                System.out.println("이미 존재하는 사이즈: " + sizeValue);
            } else {
                Size newSize = Size.builder().name(sizeValue).build();
                sizeRepository.save(newSize);
                System.out.println("새로운 사이즈 저장: " + sizeValue);
            }
        }
    }
}

