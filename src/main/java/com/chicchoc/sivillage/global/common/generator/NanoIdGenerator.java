package com.chicchoc.sivillage.global.common.generator;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

@Component
public class NanoIdGenerator {

    // 기본 길이로 NanoID 생성
    public static String generateNanoId() {
        return NanoIdUtils.randomNanoId();
    }

    // 사용자 지정 길이 및 문자 집합으로 NanoID 생성
    public static String generateNanoId(int length, char[] alphabet) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, alphabet, length);
    }
}
