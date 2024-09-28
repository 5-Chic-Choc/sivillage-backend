package com.chicchoc.sivillage.domain.review.infrastructure;

import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.global.common.utils.CursorPage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewUuid(String reviewUuid);

}
