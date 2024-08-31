package com.chicchoc.sivillage.domain.favorite.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    private Long userId;

    private Date added_at;

    private String favoriteType;

    private String favoriteUUID;
}
