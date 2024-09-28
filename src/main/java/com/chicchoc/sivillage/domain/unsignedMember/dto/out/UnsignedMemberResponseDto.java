package com.chicchoc.sivillage.domain.unsignedMember.dto.out;

import com.chicchoc.sivillage.domain.unsignedMember.domain.UnsignedMember;
import com.chicchoc.sivillage.domain.unsignedMember.vo.out.UnsignedMemberResponseVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UnsignedMemberResponseDto {

    String userUuid;

    public static UnsignedMemberResponseDto fromEntity(UnsignedMember unsignedMember) {
        return UnsignedMemberResponseDto.builder()
                .userUuid(unsignedMember.getUnsignedMemberUuid())
                .build();
    }

    public UnsignedMemberResponseVo toVo() {
        return UnsignedMemberResponseVo.builder()
                .userUuid(userUuid)
                .build();
    }
}


