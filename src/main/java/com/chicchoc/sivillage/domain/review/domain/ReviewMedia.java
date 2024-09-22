package com.chicchoc.sivillage.domain.review.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "review_media")
@Getter
@NoArgsConstructor
public class ReviewMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_media_id")
    private Long id;

    @Comment("리뷰 uuid")
    @Column(name = "review_uuid", length = 21)
    private String reviewUuid;

    @Comment("미디어 id")
    @Column(name = "media_id")
    private Long mediaId;

    @Comment("이미지 순서")
    @Column(name = "media_order")
    private int mediaOrder;

    @Builder
    public ReviewMedia(String reviewUuid, Long mediaId, int mediaOrder) {
        this.reviewUuid = reviewUuid;
        this.mediaId = mediaId;
        this.mediaOrder = mediaOrder;
    }
}

