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
public class BrandMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_media_id")
    private Long id;

    @Comment("브랜드 UUID")
    @Column(nullable = false, length = 21)
    private String brandUuid;

    @Comment("미디어 ID")
    @Column(nullable = false)
    private Long mediaId;

    @Comment("미디어 순서")
    @Column(nullable = false)
    private int mediaOrder;
}
