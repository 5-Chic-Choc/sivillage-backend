package com.chicchoc.sivillage.domain.cart.infrastructure;

import com.chicchoc.sivillage.domain.cart.domain.Cart;
import com.chicchoc.sivillage.domain.cart.dto.out.CartResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserUuid(String userUuid);

    Optional<Cart> findByUserUuidAndProductOptionUuid(String userUuid, String productOptionUuid);
}
