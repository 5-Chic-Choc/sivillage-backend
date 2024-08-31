package com.chicchoc.sivillage.domain.review.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ReviewMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private Long media_id;

    private int media_order;
}
