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

    @Comment("브랜드 이름")
    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @Comment("브랜드 로고 URL")
    @Column(nullable = false, length = 2000)
    private String logoUrl;

    public void updateBrand(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }
}
