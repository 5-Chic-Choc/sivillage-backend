package com.chicchoc.sivillage.domain.cart.infrastructure;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    List<CartProduct> findByCartUuid(String cartUuid);
}
