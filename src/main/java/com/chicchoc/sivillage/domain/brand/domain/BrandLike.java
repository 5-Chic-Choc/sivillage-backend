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
public class BrandLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_like_id")
    private Long id;

    @Comment("브랜드 UUID")
    @Column(nullable = false, length = 21)
    private String brandUuid;

    @Comment("회원 UUID")
    @Column(nullable = false, length = 21)
    private String userUuid;

    @Comment("좋아요 여부")
    @Column(nullable = false)
    private boolean isLiked;
}
