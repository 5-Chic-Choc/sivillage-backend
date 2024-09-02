package com.chicchoc.sivillage.domain.favorite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Entity
@Getter
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @Comment("회원 ID")
    @Column(nullable = false)
    private Long userId;

    @Comment("추가일")
    @Column(nullable = false)
    private Date added_at;

    @Comment("좋아요 타입")
    @Column(nullable = false, length = 30)
    private String favoriteType;

    @Comment("고유 ID")
    @Column(nullable = false, length = 50, name = "favorite_uuid")
    private String favoriteUUID;
}
