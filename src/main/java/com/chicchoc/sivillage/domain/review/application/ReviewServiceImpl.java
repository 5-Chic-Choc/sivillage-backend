package com.chicchoc.sivillage.domain.review.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.domain.member.infrastructure.MemberRepository;
import com.chicchoc.sivillage.domain.review.domain.Review;
import com.chicchoc.sivillage.domain.review.dto.in.ReviewRequestDto;
import com.chicchoc.sivillage.domain.review.dto.out.ReviewResponseDto;
import com.chicchoc.sivillage.domain.review.infrastructure.ReviewRepository;
import com.chicchoc.sivillage.domain.review.vo.out.ReviewResponseVo;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import com.chicchoc.sivillage.global.jwt.util.JwtUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final NanoIdGenerator nanoIdGenerator;
    private final MemberRepository memberRepository;

    @Override
    public void addReview(String userUuid, ReviewRequestDto reviewRequestDto) {

        Member member = memberRepository.findByUuid(userUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

        reviewRepository.save(reviewRequestDto.toEntity(nanoIdGenerator.generateNanoId(), userUuid, member.getEmail()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByProductUuid(String productUuid) {

        List<Review> reviewList = reviewRepository.findByProductUuid(productUuid);

        return ReviewResponseDto.fromEntity(reviewList).stream()
                .sorted(Comparator.comparingInt(ReviewResponseDto::getLikedCnt).reversed()).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByUserUuid(String userUuid) {

        List<Review> reviewList = reviewRepository.findByUserUuid(userUuid);

        return ReviewResponseDto.fromEntity(reviewList);
    }
}