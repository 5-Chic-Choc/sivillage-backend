package com.chicchoc.sivillage.domain.product.infrastructure;

import com.chicchoc.sivillage.domain.product.domain.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {

    Optional<Size> findByName(String name);

    Optional<Size> findByNameAndValue(String name, String value);

    @Query("SELECT s.id FROM Size s WHERE s.value = :value")
    Optional<Long> findIdByValue(@Param("value") String value);

    List<Size> findByIdIn(List<Long> ids);
}
