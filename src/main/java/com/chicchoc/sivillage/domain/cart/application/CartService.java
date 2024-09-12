package com.chicchoc.sivillage.domain.cart.application;

import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    void createCart(String userUuid);
}
