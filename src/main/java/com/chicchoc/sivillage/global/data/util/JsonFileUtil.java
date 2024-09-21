package com.chicchoc.sivillage.global.data.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.util.Arrays;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class JsonFileUtil {

    // 제네릭을 사용하여 다양한 DTO 타입으로 JSON을 변환하는 유틸리티 메소드
    public static <T> List<T> parseFileToDtoList(MultipartFile file, Class<T[]> clazz) throws IOException {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 파일을 String으로 변환
        String content = new String(file.getBytes());

        // ObjectMapper 설정 (스네이크 케이스 -> 카멜 케이스 변환)
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // JSON 문자열을 DTO 리스트로 변환
        T[] dtoArray = objectMapper.readValue(content, clazz);

        return Arrays.asList(dtoArray);
    }
}