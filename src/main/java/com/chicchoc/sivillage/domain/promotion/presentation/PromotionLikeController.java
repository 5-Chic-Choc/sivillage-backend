package com.chicchoc.sivillage.domain.promotion.presentation;

import com.chicchoc.sivillage.domain.promotion.application.PromotionLikeService;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "프로모션 좋아요", description = "프로모션 좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/promotion/like")
public class PromotionLikeController {

    private final PromotionLikeService promotionLikeService;

    @Operation(summary = "프로모션 좋아요", description = "@return : Void")
    @PostMapping("/{promotionUuid}")
    public BaseResponse<Void> likePromotion(@PathVariable String promotionUuid, Authentication authentication) {

        promotionLikeService.saveAndDeletePromotionLike(promotionUuid, authentication.getName());

        return new BaseResponse<>();
    }

    @Operation(summary = "프로모션 단건 좋아요 여부 조회", description = "@return : Boolean isLiked")
    @GetMapping("/{promotionUuid}")
    public BaseResponse<Boolean> isLikedPromotion(@PathVariable String promotionUuid, Authentication authentication) {

        boolean isLiked = promotionLikeService.isLikedPromotion(promotionUuid, authentication.getName());

        return new BaseResponse<>(isLiked);
    }

    @Operation(summary = "내가 좋아요한 프로모션 전체 조회", description = "@return : List<String promotionUuid>, 최근 좋아요한 순으로 정렬")
    @GetMapping("/all")
    public BaseResponse<List<String>> isLikedPromotion(Authentication authentication) {

        List<String> likedPromotionList = promotionLikeService.getLikedPromotionList(authentication.getName());

        return new BaseResponse<>(likedPromotionList);
    }
}
