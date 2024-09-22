package com.chicchoc.sivillage.domain.brand.presentation;

import com.chicchoc.sivillage.domain.brand.application.BrandLikeService;
import com.chicchoc.sivillage.domain.brand.application.BrandService;
import com.chicchoc.sivillage.domain.brand.dto.in.BrandRequestDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandMediaResponseDto;
import com.chicchoc.sivillage.domain.brand.dto.out.BrandResponseDto;
import com.chicchoc.sivillage.domain.brand.vo.in.BrandRequestVo;
import com.chicchoc.sivillage.domain.brand.vo.out.BrandMediaResponseVo;
import com.chicchoc.sivillage.domain.brand.vo.out.BrandResponseVo;
import com.chicchoc.sivillage.global.common.aop.annotation.CheckAuthentication;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final BrandService brandService;
    private final BrandLikeService brandLikeService;

    @Operation(summary = "createBrand API", description = "브랜드 생성", tags = {"Brand"})
    @PostMapping
    public BaseResponse<Void> createBrand(@RequestBody BrandRequestVo brandRequestVo) {
        BrandRequestDto brandRequestDto = brandRequestVo.toDto();

        brandService.addBrand(brandRequestDto);
        return new BaseResponse<>();
    }

    @Operation(summary = "getAllBrands API", description = "전체 브랜드 조회", tags = {"Brand"})
    @GetMapping()
    public BaseResponse<List<BrandResponseVo>> getBrands() {
        List<BrandResponseDto> brandResponseDtos = brandService.findAllBrands();

        List<BrandResponseVo> brandResponseVos = brandResponseDtos.stream()
                .map(BrandResponseDto::toResponseVo)
                .toList();

        return new BaseResponse<>(brandResponseVos);
    }

    @Operation(summary = "updateBrand API", description = "브랜드 수정", tags = {"Brand"})
    @PutMapping("/{brandUuid}")
    public BaseResponse<Void> updateBrand(
            @PathVariable String brandUuid,
            @RequestBody BrandRequestVo brandRequestVo) {

        BrandRequestDto brandRequestDto = brandRequestVo.toDto();
        brandService.updateBrand(brandUuid, brandRequestDto);

        return new BaseResponse<>();
    }

    @Operation(summary = "getBrand API", description = "브랜드 조회", tags = {"Brand"})
    @GetMapping("/{brandUuid}")
    public BaseResponse<BrandResponseVo> getBrand(@PathVariable String brandUuid) {
        BrandResponseDto brandResponseDto = brandService.findBrandByUuid(brandUuid);

        return new BaseResponse<>(brandResponseDto.toResponseVo());
    }

    @Operation(summary = "getAllBrandMedias API", description = "전체 브랜드 미디어 조회", tags = {"Brand"})
    @GetMapping("/media/{brandUuid}")
    public BaseResponse<List<BrandMediaResponseVo>> getBrandMedia(@PathVariable String brandUuid) {

        List<BrandMediaResponseDto> brandMediaResponseDtos = brandService.findAllBrandMedias(brandUuid);

        return new BaseResponse<>(brandMediaResponseDtos.stream()
                .map(BrandMediaResponseDto::toResponseVo)
                .toList());
    }

    @CheckAuthentication
    @Operation(summary = "BrandLike API", description = "브랜드 좋아요", tags = {"BrandLike"})
    @PostMapping("/like/{brandUuid}")
    public BaseResponse<Void> likeBrand(@PathVariable String brandUuid, Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();

        brandLikeService.saveAndUpdateBrandLike(brandUuid, userUuid);

        return new BaseResponse<>();
    }

    @CheckAuthentication
    @Operation(summary = "getBrandLike API", description = "브랜드 단건 좋아요 여부 조회", tags = {"BrandLike"})
    @GetMapping("/like/{brandUuid}")
    public BaseResponse<Boolean> isLikedBrand(@PathVariable String brandUuid, Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();

        boolean isLiked = brandLikeService.isLikedBrand(brandUuid, userUuid);

        return new BaseResponse<>(isLiked);
    }

    @CheckAuthentication
    @Operation(summary = "getAllBrandLike API",
            description = "내가 좋아요한 브랜드 전체 조회 (최신순)",
            tags = {"BrandLike"})
    @GetMapping("/like/all")
    public BaseResponse<List<String>> getLikedBrandList(Authentication authentication) {

        String userUuid = ((UserDetails) authentication.getPrincipal()).getUsername();

        List<String> likedBrandList = brandLikeService.getLikedBrandList(userUuid);

        return new BaseResponse<>(likedBrandList);
    }
}
