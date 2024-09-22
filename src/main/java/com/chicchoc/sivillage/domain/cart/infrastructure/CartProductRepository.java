package com.chicchoc.sivillage.domain.cart.infrastructure;

import com.chicchoc.sivillage.domain.cart.domain.CartProduct;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    List<CartProduct> findByCartUuid(String cartUuid);

    Optional<CartProduct> findByCartProductUuid(String cartProductUuid);

    Optional<CartProduct> findByCartUuidAndProductOptionUuid(String cartUuid, String productOptionUuid);

    void deleteByCartProductUuid(String cartProductUuid);
}
