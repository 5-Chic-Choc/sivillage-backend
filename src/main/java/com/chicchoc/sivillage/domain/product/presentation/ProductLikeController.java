package com.chicchoc.sivillage.domain.product.presentation;

import com.chicchoc.sivillage.domain.product.application.ProductLikeService;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/{productUuid}")
    public BaseResponse<Void> likeProduct(@PathVariable String productUuid, Authentication authentication) {

        if (authentication == null) {
            return new BaseResponse<>(BaseResponseStatus.NO_SIGN_IN);
        }

        String userUuid =  ((UserDetails) authentication.getPrincipal()).getUsername();

        productLikeService.saveAndUpdateProductLike(productUuid, userUuid);

        return new BaseResponse<>();
    }
}
