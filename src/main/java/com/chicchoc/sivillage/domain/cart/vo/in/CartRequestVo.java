package com.chicchoc.sivillage.domain.cart.vo.in;

public class CartRequestVo {
    // cart_id -> 받을 필요 없음
    // cart_Uuid -> 받을 필요 없음
    // isSigned -> 토큰으로 받아서 member 테이블에 확인
    // userUuid -> 토큰으로 받을거임
    private String cartName;
}
