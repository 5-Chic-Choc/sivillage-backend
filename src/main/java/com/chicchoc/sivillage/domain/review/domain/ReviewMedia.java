package com.chicchoc.sivillage.domain.review.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "review_media")
@Getter
public class ReviewMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Column(name = "media_id")
    private Long mediaId;

    @Column(name = "media_order")
    private int mediaOrder;
}
