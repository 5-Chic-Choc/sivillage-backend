package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.domain.ProductMedia;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.media.infrastructure.ProductMediaRepository;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductOrderOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final MediaRepository mediaRepository;
    private final ProductOrderOptionRepository productOrderOptionRepository;
    private final ProductMediaRepository productMediaRepository;


    @Override
    public List<ProductResponseDto> findAllByBrandId(Long brandId) {
        List<Product> products = productRepository.findAllByBrandId(brandId);

        return products.stream().map(product -> {
            String brandName = brandRepository.findById(product.getBrandId())
                    .map(Brand::getName)
                    .orElse("브랜드가 존재하지 않습니다.");

            // 현재는 price만 필요하지만 추후에 다른 정보도 필요할 수 있기에 객체를 가져옴
            ProductOrderOption productOption = productOrderOptionRepository.findFirstByProductIdOrderByIdAsc(
                            product.getId())
                    .orElseThrow(() -> new RuntimeException("일치하는 상품이 없습니다."));

            ProductMedia productMedia = productMediaRepository.findFirstByProductOrderOptionIdOrderByMediaOrderAsc(
                            productOption.getId())
                    .orElseThrow(() -> new RuntimeException("일치하는 미디어가 없습니다."));

            String mediaUrl = mediaRepository.findById(productMedia.getMediaId())
                    .map(Media::getMediaUrl)
                    .orElse("일치하는 이미지가 없습니다.");

            return ProductResponseDto.builder()
                    .productUuid(product.getProductUuid())
                    .sourceUrl(mediaUrl)
                    .brandName(brandName)
                    .productName(product.getProductName())
                    .price(productOption.getPrice())
                    .build();
        }).collect(Collectors.toList());
    }
}
