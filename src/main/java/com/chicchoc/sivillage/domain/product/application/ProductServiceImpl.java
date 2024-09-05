package com.chicchoc.sivillage.domain.product.application;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.infrastructure.CategoryRepository;
import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.domain.ProductMedia;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.media.infrastructure.ProductMediaRepository;
import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.ProductOrderOption;
import com.chicchoc.sivillage.domain.product.domain.Size;
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
    private final MediaRepository mediaRepository;
    private final ProductOrderOptionRepository productOrderOptionRepository;
    private final ProductMediaRepository productMediaRepository;
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
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getFilteredProducts(
            List<String> categories,
            List<String> sizes,
            List<String> colors,
            List<String> brands,
            Integer minPrice,
            Integer maxPrice,
            int page,
            int perPage,
            String sortBy,
            boolean isAscending) {

        List<Long> brandIds = brands != null ? brandRepository.findByNameIn(brands)
                .stream().map(Brand::getId).toList() : null;
        List<Long> colorIds = colors != null ? colorRepository.findByNameIn(colors)
                .stream().map(Color::getId).toList() : null;
        List<Long> sizeIds = sizes != null ? sizeRepository.findByNameIn(sizes)
                .stream().map(Size::getId).toList() : null;
        List<Long> categoryIds = categories != null ? categoryRepository.findByNameIn(categories)
                .stream().map(Category::getId).toList() : null;

        List<Product> products = productRepository.findFilteredProducts(
                categoryIds,
                sizeIds,
                colorIds,
                brandIds,
                minPrice,
                maxPrice,
                sortBy,
                isAscending,
                page,
                perPage
        );

        return products.stream().map(product -> {
            return ProductResponseDto.builder()
                    .productUuid(product.getProductUuid())
                    .productName(product.getProductName())
                    .price(product.getProductOrderOptions().get(0).getPrice())
                    .discountRate(product.getProductOrderOptions().get(0).getDiscountRate())
                    .discountPrice(product.getProductOrderOptions().get(0).getDiscountPrice())
                    .originalPrice(product.getProductOrderOptions().get(0).getPrice())
                    .createdAt(product.getCreatedAt())
                    .brandId(product.getBrandId())
                    .build();
        }).collect(Collectors.toList());
    }


}
