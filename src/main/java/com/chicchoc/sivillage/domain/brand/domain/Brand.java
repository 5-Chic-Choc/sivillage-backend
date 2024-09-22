package com.chicchoc.sivillage.domain.brand.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Comment("브랜드 UUID")
    @Column(nullable = false, length = 21, unique = true)
    private String brandUuid;

    @Comment("브랜드 한글 인덱스 문자")
    @Column(nullable = false, length = 255)
    private String name;

    @Comment("브랜드 이름")
    @Column(nullable = false, length = 255)
    private String nameKo;

    @Comment("브랜드 로고 URL")
    @Column(nullable = true, length = 2000)
    private String logoUrl;

    @Comment("브랜드 리스트 타입")
    @Column(nullable = false, length = 10)
    private String brandListType;

    @Comment("브랜드 인덱스 문자")
    @Column(nullable = false, length = 10)
    private String brandIndexLetter;

    public void updateBrand(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    @Builder
    public Brand(String brandUuid, String name, String nameKo, String logoUrl, String brandListType,
                 String brandIndexLetter) {
        this.brandUuid = brandUuid;
        this.name = name;
        this.nameKo = nameKo;
        this.logoUrl = logoUrl;
        this.brandListType = brandListType;
        this.brandIndexLetter = brandIndexLetter;
    }
}
