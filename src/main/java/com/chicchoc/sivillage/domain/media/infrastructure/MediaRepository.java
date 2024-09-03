package com.chicchoc.sivillage.domain.media.infrastructure;

import com.chicchoc.sivillage.domain.media.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
