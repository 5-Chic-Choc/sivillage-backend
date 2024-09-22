package com.chicchoc.sivillage.global.data.application;


import static com.chicchoc.sivillage.global.data.util.SizeOptionClassifier.getMappedSizeName;
import static com.chicchoc.sivillage.global.data.util.SizeOptionClassifier.isFreeSize;
import static com.chicchoc.sivillage.global.data.util.SizeOptionClassifier.isValidSize;

import com.chicchoc.sivillage.domain.brand.domain.Brand;
import com.chicchoc.sivillage.domain.brand.infrastructure.BrandRepository;
import com.chicchoc.sivillage.domain.category.domain.Category;
import com.chicchoc.sivillage.domain.category.domain.ProductCategory;
import com.chicchoc.sivillage.domain.category.infrastructure.CategoryRepository;
import com.chicchoc.sivillage.domain.category.infrastructure.ProductCategoryRepository;
import com.chicchoc.sivillage.domain.media.domain.Media;
import com.chicchoc.sivillage.domain.media.infrastructure.MediaRepository;
import com.chicchoc.sivillage.domain.product.domain.Color;
import com.chicchoc.sivillage.domain.product.domain.EtcOption;
import com.chicchoc.sivillage.domain.product.domain.Product;
import com.chicchoc.sivillage.domain.product.domain.ProductDetail;
import com.chicchoc.sivillage.domain.product.domain.ProductHashtag;
import com.chicchoc.sivillage.domain.product.domain.ProductMedia;
import com.chicchoc.sivillage.domain.product.domain.ProductOption;
import com.chicchoc.sivillage.domain.product.domain.SaleStatus;
import com.chicchoc.sivillage.domain.product.domain.Size;
import com.chicchoc.sivillage.domain.product.infrastructure.ColorRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.EtcOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductDetailRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductHashtagRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductMediaRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductOptionRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.ProductRepository;
import com.chicchoc.sivillage.domain.product.infrastructure.SizeRepository;
import com.chicchoc.sivillage.global.common.aop.annotation.TimeAop;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import com.chicchoc.sivillage.global.data.dto.product.ProductDataRequestDto;
import com.chicchoc.sivillage.global.data.dto.product.ProductDataRequestDto.ImageDto;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDataService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductHashtagRepository productHashtagRepository;

    private final ProductMediaRepository productMediaRepository;
    private final MediaRepository mediaRepository;
    private final ProductDetailRepository productDetailRepository;

    private final ColorRepository colorRepository;
    private final ColorDataService colorDataService;
    private final SizeRepository sizeRepository;
    private final EtcOptionRepository etcOptionRepository;
    private final ProductOptionRepository productOptionRepository;

    private final EntityManager entityManager;

    /**
     * 상품 데이터 데이터가 담긴 List(Json 파일)을 순회하며 총 12개의 테이블에 데이터 저장하는 메서드.
     * <p>
     * 상품, 브랜드, 카테고리(+중간테이블), 해시태그, 미디어(+중간테이블), 상세이미지, 색상, 사이즈, 기타 옵션, 제품 옵션
     * </p>
     *
     * @param dtos : 상품 데이터를 받는 DTO 리스트
     */

    @TimeAop
    @Transactional
    public void saveProductData(List<ProductDataRequestDto> dtos) {

        int batchSize = 50;  // 배치 크기 설정
        int count = 0;

        for (ProductDataRequestDto dto : dtos) {
            log.info("dto = " + dto);

            if (checkNull(dto)) {
                continue;
            }

            // Step 1. 상품 Entity 저장
            String productUuid = dto.getGoodsNo();
            Product product = saveProduct(dto, productUuid);

            // Step 22. 제품 카테고리 중간테이블 저장
            saveCategory(dto, product);

            // Step 3. 제품 해시태그 저장
            saveHashtags(dto.getHashtags(), productUuid);

            // Step 4. 제품 옵션 Entity 관련 데이터 저장(색상, 사이즈, 기타 옵션, 이미지, 상세이미지)
            // 색상, 사이즈가 없으면 빈 리스트를 대신 넣어줌(n * m = 0 방지)
            List<String> colors = dto.getColors().isEmpty() ? List.of("") : dto.getColors();
            List<String> sizes = dto.getSizes().isEmpty() ? List.of("") : dto.getSizes();

            // 하나의 제품에서, 색상 n개, 사이즈 m개를 가지면 n * m개의 제품 옵션을 가짐 => 이중 for문
            for (String color : colors) {
                for (String size : sizes) {
                    String productOptUuid = new NanoIdGenerator().generateNanoId();

                    // Step 4-1. 제품 이미지 저장
                    saveImages(dto.getImages(), productOptUuid);

                    // Step 4-2. 제품 상세 이미지 저장(상세 정보의 iframe URL)
                    productDetailRepository.save(ProductDetail.builder()
                            .productOptionUuid(productOptUuid)
                            .productDetailUrl(dto.getDetailIframe())
                            .build());

                    // 4-3. 제품 색상 매핑 또는 저장
                    Long colorId = saveColor(color);

                    // 4-4. 제품 사이즈/기타옵션 매핑 또는 저장
                    Long sizeId = 0L;
                    if (!size.isEmpty()) {
                        sizeId = sizeRepository.findIdByValue(size) //사이즈가 이미 존재하면 해당 ID 반환
                                // 없으면 저장, 지정된 사이즈가 아닐 경우 0반환
                                .orElseGet(() -> saveSizeOrOption(size));
                    }

                    // 4-5.기타 옵션 저장(사이즈가 0일 경우)
                    Long etcOptionId = sizeId == 0L ? saveEtcOption(size) : null;

                    Integer normalPrice = dto.getNormalPrice();
                    Integer discountPrice = dto.getDiscountPrice();
                    Integer discountRate =
                            discountPrice != null ? (int) ((1 - (double) discountPrice / normalPrice) * 100) : null;

                    productOptionRepository.save(ProductOption.builder()
                            .productOptionUuid(productOptUuid)
                            .product(product)
                            .productUuid(productUuid)
                            .sizeId(sizeId)
                            .colorId(colorId)
                            .etcOptionId(etcOptionId)
                            .saleStatus(SaleStatus.ON_SALE) //판매 상태는 모두 판매중으로 저장
                            .price(normalPrice)
                            .discountPrice(discountPrice)
                            .discountRate(discountRate)
                            .build());
                }
            }
            count++;
            System.out.println("============ count ============ ::::::: " + count);
            if (count % batchSize == 0) {
                log.info("Batch size reached. Flushing changes and clearing the entity manager.");
                productRepository.flush();
                entityManager.clear();
            }
        }
    }

    // ==================== 아래는 Private 메서드 =====================

    private boolean checkNull(ProductDataRequestDto dto) {
        try {
            // 필수 데이터가 null인 경우
            if (dto.getGoodsNo() == null || dto.getGoodsNm() == null || dto.getBrandNm() == null
                    || dto.getDispLctgNm() == null || dto.getNormalPrice() == null) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    // 브랜드 찾아 상품 저장
    private Product saveProduct(ProductDataRequestDto dto, String productUuid) {

        Optional<Product> existingProduct = productRepository.findByProductUuid(productUuid);

        // 브랜드 이름이 같고, 브랜드 리스트 타입이 "en"인 브랜드의 UUID를 찾음
        String brandUuid = brandRepository.findTopBrandUuidByNameAndBrandListType(dto.getBrandNm())
                // 없으면 새로 저장
                .orElseGet(() -> {
                    Brand newBrand = brandRepository.save(Brand.builder()
                            .brandUuid(new NanoIdGenerator().generateNanoId())
                            .name(dto.getBrandNm() != null ? dto.getBrandNm() : "데이터 넣기 힘듭니다.")
                            .brandIndexLetter(dto.getBrandNm().substring(0, 1))
                            .brandListType("en")
                            .nameKo("이걸 본다면 당신은 대단한 사람입니다.")
                            .build());
                    return newBrand.getBrandUuid(); // 생성된 brandUuid 반환
                });

        return productRepository.save(Product.builder()
                .productUuid(productUuid)
                .brandUuid(brandUuid)
                .productName(dto.getGoodsNm())
                .build());
    }

    // 카테고리 저장
    private void saveCategory(ProductDataRequestDto dto, Product product) {
        Category finalCategory = findFinalCategory(dto.getDispLctgNm(), dto.getDispMctgNm(),
                dto.getDispSctgNm(), dto.getDispDctgNm());

        // 종종 데이터에 카테고리가 없는 경우가 있음 => 카테고리가 없으면 저장하지 않음
        if (finalCategory == null) {
            log.error("카테고리가 존재하지 않습니다.");
        }

        productCategoryRepository.save(ProductCategory.builder()
                .productId(product.getId())
                .categoryId(finalCategory.getId())
                .build());

    }

    // 해시태그 저장(일괄 저장 -> DB 호출 최소화)
    private void saveHashtags(List<String> hashtags, String productUuid) {
        List<ProductHashtag> productHashtags = new ArrayList<>();
        for (String tag : hashtags) {
            tag = tag.length() > 30 ? tag.substring(0, 30) : tag; // 해시태그 길이 제한
            productHashtags.add(ProductHashtag.builder()
                    .productUuid(productUuid)
                    .hashtagContent(tag)
                    .build());
        }
        productHashtagRepository.saveAll(productHashtags);
    }

    // 미디어 및 제품미디어 저장(일괄 저장 -> DB 호출 최소화)
    private void saveImages(List<ImageDto> dto, String productOptUuid) {
        List<ProductMedia> productMediaList = new ArrayList<>();
        List<Media> mediaList = new ArrayList<>();
        Map<ImageDto, Media> imageMediaMap = new HashMap<>();

        for (ImageDto image : dto) {
            Media media = Media.builder()
                    .mediaUrl(image.getSrc())
                    .mediaType("img")
                    .build();
            mediaList.add(media);
            imageMediaMap.put(image, media);
        }
        mediaRepository.saveAll(mediaList);  // 한 번에 저장

        for (Entry<ImageDto, Media> entry : imageMediaMap.entrySet()) {
            ImageDto image = entry.getKey();
            Media media = entry.getValue();

            productMediaList.add(ProductMedia.builder()
                    .productOptionUuid(productOptUuid)
                    .mediaId(media.getId())
                    .mediaOrder(image.getIdx())
                    .build());
        }
        productMediaRepository.saveAll(productMediaList);  // 한 번에 저장
    }

    // colorId를 찾음. 없으면 새로 저장
    private Long saveColor(String color) {
        if (color.isEmpty()) {
            return null;
        }
        Long colorId = colorRepository.findIdByValue(color)
                .orElseGet(() -> colorRepository.save(
                        Color.builder()
                                .name(colorDataService.matchColorName(color))
                                .value(color)
                                .build()).getId());
        return colorId;
    }

    // 사이즈 저장 또는 0L 반환(기타 옵션으로 판단)
    private Long saveSizeOrOption(String size) {
        if (isFreeSize(size)) {
            return saveSize("FREE", size);
        } else if (isValidSize(size)) {
            return saveSize(getMappedSizeName(size), size);  // 지정된 사이즈 범주에 해당하면 저장
        } else {
            // 0을 반환 => 기타 옵션으로 저장
            return 0L;
        }
    }

    // Size 엔티티 저장 로직
    private Long saveSize(String name, String value) {
        // 이미 존재하는 사이즈인지 확인, 존재하면 해당 ID 반환
        Optional<Size> existSize = sizeRepository.findByNameAndValue(name, value);
        if (existSize.isPresent()) {
            return existSize.get().getId();
        }

        Size size = Size.builder()
                .name(name)
                .value(value)
                .build();
        try {
            return sizeRepository.save(size).getId();
        } catch (Exception e) {
            System.out.println("value = " + value);
            return 0L;
        }
    }

    // EtcOption 엔티티 저장 로직
    private Long saveEtcOption(String value) {
        try {
            return etcOptionRepository.save(EtcOption.builder()
                    .name(value)
                    .build()).getId();
        } catch (Exception e) {
            System.out.println("value = " + value);
            return null;
        }
    }

    // 부모 카테고리 이름부터 시작해서 최종 카테고리 찾기
    private Category findFinalCategory(String largeCategoryName, String middleCategoryName, String smallCategoryName,
            String detailCategoryName) {

        // Step 1: Large Category (루트 카테고리)
        Category largeCat = categoryRepository.findRootCategoryByName(largeCategoryName)
                .orElseGet(() -> categoryRepository.findTopByName(largeCategoryName)
                        .orElse(null));

        // Step 2: Middle Category
        Category middleCat = categoryRepository.findByNameAndParent(middleCategoryName, largeCat)
                .orElse(null);

        // Step 3: Small Category(+null 체크)
        if (smallCategoryName == null || smallCategoryName.trim().isEmpty()) {
            return middleCat;
        }

        Category smallCat = categoryRepository.findByNameAndParent(smallCategoryName, middleCat)
                .orElse(null);

        // Step 4: Detail Category(+null 체크)
        if (detailCategoryName == null || detailCategoryName.trim().isEmpty()) {
            return smallCat;
        }

        Category detailCat = categoryRepository.findByNameAndParent(detailCategoryName, smallCat)
                .orElse(null);

        return detailCat;
    }
}
