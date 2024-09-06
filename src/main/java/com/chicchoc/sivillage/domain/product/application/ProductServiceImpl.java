package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.infrastructure.CategoryRepository;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.media.infrastructure.ProductMediaRepository;
import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.dto.in.ProductRequestDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductDetailResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductPerBrandResponseDto;
import com.chicchoc.sivillage.domain.product.dto.out.ProductResponseDto;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductOrderOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
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
    private final ProductOrderOptionRepository productOrderOptionRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductPerBrandResponseDto> findAllByBrandId(Long brandId) {
        List<Product> products = productRepository.findAllByBrandId(brandId);

        return products.stream().map(product -> {
            String brandName = brandRepository.findById(product.getBrandId())
                    .map(Brand::getName)
                    .orElse("브랜드가 존재하지 않습니다.");

            ProductOrderOption productOption = productOrderOptionRepository.findFirstByProductIdOrderByIdAsc(
                            product.getId())
                    .orElseThrow(() -> new RuntimeException("일치하는 상품이 없습니다."));

            return ProductPerBrandResponseDto.builder()
                    .productUuid(product.getProductUuid())
                    .brandName(brandName)
                    .productName(product.getProductName())
                    .price(productOption.getPrice())
                    .discountPrice(productOption.getDiscountPrice())
                    .discountRate(productOption.getDiscountRate())
                    .createdAt(product.getCreatedAt())
                    .brandId(product.getBrandId())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getFilteredProducts(ProductRequestDto productRequestDto) {

        // 각 리스트의 이름을 ID 리스트로 변환
        List<Long> brandIds = productRequestDto.getBrands() != null ? brandRepository
                .findByNameIn(productRequestDto.getBrands())
                .stream().map(Brand::getId).toList() : null;
        List<Long> colorIds = productRequestDto.getColors() != null ? colorRepository
                .findByNameIn(productRequestDto.getColors())
                .stream().map(Color::getId).toList() : null;
        List<Long> sizeIds = productRequestDto.getSizes() != null ? sizeRepository
                .findByNameIn(productRequestDto.getSizes())
                .stream().map(Size::getId).toList() : null;
        List<Long> categoryIds = productRequestDto.getCategories() != null ? categoryRepository
                .findByNameIn(productRequestDto.getCategories())
                .stream().map(Category::getId).toList() : null;

        // 필터링된 상품 목록 조회
        List<Product> products = productRepository.findFilteredProducts(
                categoryIds,
                sizeIds,
                colorIds,
                brandIds,
                productRequestDto.getMinimumPrice(),
                productRequestDto.getMaximumPrice(),
                productRequestDto.getSortBy(),
                productRequestDto.isAscending(),
                productRequestDto.getPage(),
                productRequestDto.getPerPage()
        );

        // DTO 변환
        return products.stream().map(product -> ProductResponseDto.builder()
                .productUuid(product.getProductUuid())
                .productName(product.getProductName())
                .price(product.getProductOrderOptions().get(0).getPrice())
                .discountRate(product.getProductOrderOptions().get(0).getDiscountRate())
                .discountPrice(product.getProductOrderOptions().get(0).getDiscountPrice())
                .createdAt(product.getCreatedAt())
                .brandId(product.getBrandId())
                .build()).collect(Collectors.toList());
    }
}
