package com.chicchoc.sivillage.domain.unsignedMember.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnsignedMemberResponseVo {
    String userUuid;

    @Builder
    public UnsignedMemberResponseVo(String userUuid) {
        this.userUuid = userUuid;
    }
}
