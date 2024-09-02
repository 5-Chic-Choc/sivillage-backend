package com.chicchoc.sivillage.domain.hashtag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hashtag_id")
    private Long id;

    @Comment("해시태그 내용")
    @Column(nullable = false, length = 30)
    private String content;
}
