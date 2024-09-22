package com.chicchoc.sivillage.global.data.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDataRequestDto {

    private String goodsNo;          // 상품 번호
    private String goodsNm;          // 상품명
    private String brandNm;          // 브랜드명
    private Integer normalPrice;     // 정상가
    private Integer discountPrice;   // 할인가 (nullable)

    private String dispLctgNm;       // 대분류 카테고리명
    private String dispMctgNm;       // 중분류 카테고리명
    private String dispSctgNm;       // 소분류 카테고리명
    private String dispDctgNm;       // 소분류 카테고리명 (nullable)

    private String detailIframe;     // 상품 상세 설명 iframe URL

    private List<String> colors;     // 색상 리스트
    private List<String> sizes;      // 사이즈 리스트
    private List<String> hashtags;   // 해시태그 리스트

    private List<ImageDto> images;   // 이미지 리스트

    @Getter
    @Setter
    @ToString
    public static class ImageDto {
        private Integer idx;         // 이미지 순서
        private String src;          // 이미지 URL
    }

    /*
    // 상품 데이터 샘플
    {
        "goods_no": "2101332910",
        "goods_nm": "g.Deacon 남성 골프 퍼포먼스 피케 폴로 셔츠",
        "brand_nm": "GLENMUIR",
        "normal_price": 180000,
        "discount_price": null,
        "disp_lctg_nm": "골프",
        "disp_mctg_nm": "남성 골프웨어",
        "disp_sctg_nm": "티셔츠",
        "disp_dctg_nm": "카라/폴로형",
        "colors": [
            "화이트",
            "네이비",
            "레드",
            "와인",
            "블랙",
            "라이트 블루",
            "그레이"
        ],
        "sizes": [
            "XS",
            "S",
            "M",
            "L",
            "XL"
        ],
        "detail_iframe": "https://m-goods.sivillage.com/goods/getGoodDescCont.siv?goods_no=2101332910",
        "hashtags": [
            "#반팔",
            "#칼라넥",
            "#캐주얼"
        ],
        "images": [
            {
                "idx": 0,
                "src": "https://image.sivillage.com/upload/C00001/goods/org/175/210120001030175.jpg?RS=750&SP=1"
            }
        ]
    },
    }*/

}
