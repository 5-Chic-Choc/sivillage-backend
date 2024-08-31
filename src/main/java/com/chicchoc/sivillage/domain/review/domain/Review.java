package com.chicchoc.sivillage.domain.review.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Long id;

    private Long user_id;

    private Long product_id;

    private String size;

    private String option;

    private String info;

    private int rate;

    private String content;

    private Date created_at;

    private Date updated_at;
}
