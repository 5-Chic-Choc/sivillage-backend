package com.chicchoc.sivillage.global.data.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SizeOptionClassifier {

    // 패턴 정의
    private static final Pattern SIZE_NAME_PATTERN = Pattern.compile(
            "^(XXXS|XXS|XS|S|M|L|XL|XXL|XXXL|\\d{2,3}mm|\\d{2,3}|IT \\d{2,3}|FR \\d{2,3}|EU \\d{2,3}|UK \\d{2,3}|)$"
    );
    private static final Pattern FREE_SIZE_PATTERN = Pattern.compile(
            "^(free|FREE|Free|단품|OS|One|OneSize|ONESIZE|F|FR|U|UNI|공통|단일|SMALL|LARGE|MEDIUM)$"
    );


    // 지정된 사이즈 범주에 맞는지 확인
    public static boolean isValidSize(String size) {
        return SIZE_NAME_PATTERN.matcher(size).matches();
    }

    // FREE로 처리할 사이즈 확인
    public static boolean isFreeSize(String size) {
        return FREE_SIZE_PATTERN.matcher(size).matches();
    }


    public static String getMappedSizeName(String value) {
        // 의류 사이즈 매핑
        for (Map.Entry<String, String> entry : sizeToInternationalMap.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();  // 기준 사이즈 반환 (예: M, L, XL)
            }
        }
        // 신발 사이즈 매핑
        for (Map.Entry<String, String> entry : shoeSizeToInternationalMap.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();  // 신발 사이즈의 기준 값 반환 (예: 260)
            }
        }

        // 하의 사이즈 매핑
        for (Map.Entry<String, String> entry : pantsSizeToInternationalMap.entrySet()) {
            if (entry.getValue().contains(value)) {
                return entry.getKey();  // 하의 사이즈의 기준 값 반환 (예: 30)
            }
        }
        return value;  // 기준 값이 없으면 그대로 반환
    }

    private static final Map<String, String> sizeToInternationalMap = new HashMap<>();
    private static final Map<String, String> shoeSizeToInternationalMap = new HashMap<>();
    private static final Map<String, String> pantsSizeToInternationalMap = new HashMap<>();

    static {
        // 의류 사이즈 매핑 (기준 값 예시)
        // 의류 사이즈 매핑 (국제 사이즈 표 포함)
        sizeToInternationalMap.put("XXXS", "US: 00, EU: 28, IT: 32, FR: 30");
        sizeToInternationalMap.put("XXS", "US: 0, EU: 30, IT: 34, FR: 32");
        sizeToInternationalMap.put("XS", "US: 2, EU: 32, IT: 36, FR: 34");
        sizeToInternationalMap.put("S", "US: 4, EU: 34, IT: 38, FR: 36");
        sizeToInternationalMap.put("M", "US: 6, EU: 36, IT: 40, FR: 38");
        sizeToInternationalMap.put("L", "US: 8, EU: 38, IT: 42, FR: 40");
        sizeToInternationalMap.put("XL", "US: 10, EU: 40, IT: 44, FR: 42");
        sizeToInternationalMap.put("XXL", "US: 12, EU: 42, IT: 46, FR: 44");
        sizeToInternationalMap.put("XXXL", "US: 14, EU: 44, IT: 48, FR: 46");
        sizeToInternationalMap.put("4XL", "US: 16, EU: 46, IT: 50, FR: 48");
        sizeToInternationalMap.put("5XL", "US: 18, EU: 48, IT: 52, FR: 50");

        // 하의, 허리 사이즈 예시 (인치 기준, US, EU, UK, KR 포함)
        pantsSizeToInternationalMap.put("28", "US: 28, EU: 44, UK: 28, KR: 72cm");
        pantsSizeToInternationalMap.put("30", "US: 30, EU: 46, UK: 30, KR: 76cm");
        pantsSizeToInternationalMap.put("32", "US: 32, EU: 48, UK: 32, KR: 81cm");
        pantsSizeToInternationalMap.put("34", "US: 34, EU: 50, UK: 34, KR: 86cm");
        pantsSizeToInternationalMap.put("36", "US: 36, EU: 52, UK: 36, KR: 91cm");
        pantsSizeToInternationalMap.put("38", "US: 38, EU: 54, UK: 38, KR: 97cm");

        // 신발 사이즈 매핑 (MM 단위 기준 값)
        shoeSizeToInternationalMap.put("210", "US: 4, EU: 34, UK: 2");
        shoeSizeToInternationalMap.put("215", "US: 4.5, EU: 34.5, UK: 2.5");
        shoeSizeToInternationalMap.put("220", "US: 5, EU: 35, UK: 3");
        shoeSizeToInternationalMap.put("225", "US: 5.5, EU: 36, UK: 3.5");
        shoeSizeToInternationalMap.put("230", "US: 6.5, EU: 37, UK: 4.5");
        shoeSizeToInternationalMap.put("235", "US: 7, EU: 37.5, UK: 5");
        shoeSizeToInternationalMap.put("240", "US: 7.5, EU: 38, UK: 5.5");
        shoeSizeToInternationalMap.put("245", "US: 8, EU: 38.5, UK: 6");
        shoeSizeToInternationalMap.put("250", "US: 8.5, EU: 39, UK: 6.5");
        shoeSizeToInternationalMap.put("255", "US: 9, EU: 40, UK: 7");
        shoeSizeToInternationalMap.put("260", "US: 9.5, EU: 41, UK: 7.5");
        shoeSizeToInternationalMap.put("265", "US: 10, EU: 42, UK: 8");
        shoeSizeToInternationalMap.put("270", "US: 10.5, EU: 43, UK: 8.5");
        shoeSizeToInternationalMap.put("275", "US: 11, EU: 43.5, UK: 9");
        shoeSizeToInternationalMap.put("280", "US: 11.5, EU: 44, UK: 9.5");
        shoeSizeToInternationalMap.put("285", "US: 12, EU: 44.5, UK: 10");
        shoeSizeToInternationalMap.put("290", "US: 12.5, EU: 45, UK: 10.5");
        shoeSizeToInternationalMap.put("295", "US: 13, EU: 46, UK: 11");
        shoeSizeToInternationalMap.put("300", "US: 13.5, EU: 47, UK: 11.5");

    }
}