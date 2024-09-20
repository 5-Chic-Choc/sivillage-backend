package com.chicchoc.sivillage.global.data.application;

import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.data.dto.ColorType;
import com.chicchoc.sivillage.global.data.dto.ProductColorRequestDto;
import com.chicchoc.sivillage.global.data.dto.ProductSizeRequestDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductDataService {

    private final ProductRepository productRepository;
    private final NanoIdGenerator nanoIdGenerator;
    private final ColorRepository colorRepository;
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


    //상품 색상 데이터 저장
    @Transactional
    public void saveColorData(List<ProductColorRequestDto> colorDtos) {
        colorDtos.forEach(dto -> {
            String colorValue = dto.getColorValue();
            String matchedColorName = matchColorName(colorValue);

            // DB에 이미 해당 색상이 있는지 확인
            Optional<Color> existingColor = colorRepository.findByNameAndValue(matchedColorName, colorValue);

            // 색상이 이미 존재하면 업데이트 또는 로그 처리, 없으면 새로 저장
            existingColor.ifPresentOrElse(
                    color -> System.out.println("이미 존재하는 색상: " + matchedColorName + ", 값: " + colorValue), () -> {
                        Color newColor = Color.builder().name(matchedColorName).value(colorValue).build();
                        colorRepository.save(newColor);
                        System.out.println("새로운 색상 저장: " + matchedColorName + ", 값: " + colorValue);
                    });
        });
    }

    // Enum 기반 색상 필터링 이름 매핑
    private static final Map<String, List<String>> colorKeywordMapping = new HashMap<>() {
        {
            put(ColorType.WHITE.getName(),
                    List.of("화이트", "투명", "퓨어", "아이보리", "크림", "페일 화이트"));
            put(ColorType.BEIGE.getName(),
                    List.of("베이지", "크림", "샌드", "누드", "페일 베이지"));
            put(ColorType.GRAY.getName(),
                    List.of("그레이", "스페이스 그레이", "차콜", "애쉬", "라이트 그레이", "다크 그레이", "스모크 그레이"));
            put(ColorType.BLACK.getName(),
                    List.of("블랙", "제트 블랙", "다크 블랙", "미드나잇 블랙", "매트 블랙"));
            put(ColorType.BROWN.getName(),
                    List.of("브라운", "초콜릿", "다크 브라운", "라이트 브라운", "코코아", "모카", "카멜"));
            put(ColorType.RED.getName(),
                    List.of("레드", "버건디", "다크 레드", "체리", "크림슨", "스칼렛", "루비"));
            put(ColorType.PINK.getName(),
                    List.of("핑크", "로즈", "피치 핑크", "샐먼 핑크", "핫 핑크", "라이트 핑크", "매지컬 핑크"));
            put(ColorType.ORANGE.getName(),
                    List.of("오렌지", "코랄", "피치", "라이트 오렌지", "딥 오렌지", "앰버"));
            put(ColorType.YELLOW.getName(),
                    List.of("옐로우", "머스터드", "골든 옐로우", "라이트 옐로우", "선샤인 옐로우", "레몬 옐로우"));
            put(ColorType.GREEN.getName(),
                    List.of("그린", "민트", "올리브 그린", "포레스트 그린", "라이트 그린", "다크 그린", "네온 그린", "라임"));
            put(ColorType.BLUE.getName(),
                    List.of("블루", "라이트 블루", "네이비 블루", "스카이 블루", "아쿠아", "티파니 블루", "파우더 블루", "딥 블루"));
            put(ColorType.PURPLE.getName(),
                    List.of("퍼플", "라일락", "바이올렛", "라벤더", "매지컬 퍼플", "플럼", "라일락 퍼플"));
            put(ColorType.GOLD.getName(),
                    List.of("골드", "샴페인 골드", "로즈 골드", "메탈릭 골드", "옐로우 골드"));
            put(ColorType.SILVER.getName(),
                    List.of("실버", "메탈릭 실버", "스털링 실버", "그레이 실버", "차콜 실버", "라이트 실버"));
            put(ColorType.OTHER.getName(),
                    List.of("멀티", "레인보우", "믹스", "다채색"));
        }
    };

    // 색상 필터링 이름을 찾아주는 로직
    private String matchColorName(String colorValue) {
        // color_value가 특정 키워드를 포함하는지 확인
        for (Map.Entry<String, List<String>> entry : colorKeywordMapping.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (colorValue.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }
        return ColorType.OTHER.getName(); // 매칭되지 않으면 '기타'로 설정
    }


}

