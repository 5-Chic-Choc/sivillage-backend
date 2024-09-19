package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ProductLikeService;
import com.chicchoc.sivillage.global.common.aop.annotation.CheckAuthentication;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 좋아요", description = "상품 좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/like")
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @CheckAuthentication
    @Operation(summary = "상품 좋아요", description = "@return : Void")
    @PostMapping("/{productUuid}")
    public BaseResponse<Void> likeProduct(@PathVariable String productUuid, Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();
        productLikeService.saveAndUpdateProductLike(productUuid, userUuid);

        return new BaseResponse<>();
    }

    @CheckAuthentication
    @Operation(summary = " 상품 단건 좋아요 여부 조회", description = "@return : Boolean isLiked")
    @GetMapping("/{productUuid}")
    public BaseResponse<Boolean> isLikedProduct(@PathVariable String productUuid, Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();
        boolean isLiked = productLikeService.isLikedProduct(productUuid, userUuid);

        return new BaseResponse<>(isLiked);
    }

    @CheckAuthentication
    @Operation(summary = "내가 좋아요한 상품 전체 조회", description = "@return : List<String productUuid>, 최근 좋아요한 순으로 정렬")
    @GetMapping("/all")
    public BaseResponse<List<String>> isLikedProduct(Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<String> likedProductList = productLikeService.getLikedProductList(userUuid);

        return new BaseResponse<>(likedProductList);
    }
}
