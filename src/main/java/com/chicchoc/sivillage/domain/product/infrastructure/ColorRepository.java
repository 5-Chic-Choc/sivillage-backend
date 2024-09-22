package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Color;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColorRepository extends JpaRepository<Color, Long> {

    Optional<Color> findByNameAndValue(String name, String value);

    @Query("SELECT c.id FROM Color c WHERE c.value = :value")
    Optional<Long> findIdByValue(@Param("value") String value);
}
