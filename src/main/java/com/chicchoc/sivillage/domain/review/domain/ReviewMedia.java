package com.chicchoc.sivillage.domain.review.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "review_uuid")
    private String reviewUuid;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "media_order")
    private int mediaOrder;

    @Builder
    public ReviewMedia(String reviewUuid, Long mediaId, int mediaOrder) {
        this.reviewUuid = reviewUuid;
        this.mediaId = mediaId;
        this.mediaOrder = mediaOrder;
    }
}

