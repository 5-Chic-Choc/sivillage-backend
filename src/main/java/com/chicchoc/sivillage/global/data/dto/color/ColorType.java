package com.chicchoc.sivillage.global.data.dto.color;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ColorType {
    WHITE("화이트"),
    BEIGE("베이지"),
    GRAY("그레이"),
    BLACK("블랙"),
    BROWN("브라운"),
    RED("레드"),
    PINK("핑크"),
    ORANGE("오렌지"),
    YELLOW("옐로우"),
    GREEN("그린"),
    BLUE("블루"),
    PURPLE("퍼플"),
    GOLD("골드"),
    SILVER("실버"),
    OTHER("기타");

    private final String name;


    // Enum에서 문자열을 기준으로 ColorType을 찾는 메서드
    public static ColorType fromName(String name) {
        for (ColorType type : ColorType.values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return OTHER; // 매칭되지 않으면 '기타'로 설정
    }
}
