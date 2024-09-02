package com.chicchoc.sivillage.domain.brand.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_id")
    private Long id;

    @Comment("브랜드 이름")
    @Column(nullable = false, length = 255, unique = true)
    private String name;

    @Comment("브랜드 로고 URL")
    @Column(nullable = false, length = 2000)
    private String logoUrl;
}
