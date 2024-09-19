package com.chicchoc.sivillage.domain.review.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_media")
@Getter
@NoArgsConstructor
public class ReviewMedia extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review", nullable = false)
    private Review review;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "media_order")
    private int mediaOrder;

    @Builder
    public ReviewMedia(Long id, Review review, Long mediaId, int mediaOrder) {
        this.review = review;
        this.mediaId = mediaId;
        this.mediaOrder = mediaOrder;
    }
}

