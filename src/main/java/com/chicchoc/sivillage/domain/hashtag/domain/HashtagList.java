package com.chicchoc.sivillage.domain.hashtag.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class HashtagList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hashtag_list_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    // Enum?? 수정 필요
    @Comment("해시태그 타입")
    @Column(nullable = false, length = 30)
    private String hashtagType;

    @Comment("해시태그 UUID")
    @Column(nullable = false, length = 50, name = "hashtag_uuid")
    private String hashtagUUID;
}
