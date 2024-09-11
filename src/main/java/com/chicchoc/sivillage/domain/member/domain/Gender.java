package com.chicchoc.sivillage.domain.member.domain;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {

    GENDER_MALE("남성"),
    GENDER_FEMALE("여성"),
    GENDER_ETC("기타");

    private final String gender;

    @JsonValue
    public String getGender() {
        return gender;
    }

    @JsonCreator
    public static Gender fromString(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.gender.equals(value)) {
                return gender;
            }
        }
        throw new BaseException(BaseResponseStatus.INVALID_INPUT_VALUE);
    }
}
