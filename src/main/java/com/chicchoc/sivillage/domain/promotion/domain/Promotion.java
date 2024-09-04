package com.chicchoc.sivillage.domain.promotion.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promotion_id")
    private Long id;

    @Comment("제목")
    @Column(nullable = false, length = 100)
    private String title;

    @Comment("프로모션 간단 설명")
    @Column(nullable = false, length = 255)
    private String description;

    @Comment("프로모션 상세 내용")
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("썸네일 이미지 URL")
    @Column(nullable = false, length = 2000)
    private String thumbnailUrl;
}
