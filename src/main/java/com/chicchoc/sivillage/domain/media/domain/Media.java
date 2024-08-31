package com.chicchoc.sivillage.domain.media.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "media_id")
    private Long id;

    @Comment("미디어 URL")
    @Column(nullable = false, length = 2000)
    private String media_url;

    @Comment("미디어 종류")
    @Column(nullable = false, length = 20)
    private String media_type;

    @Comment("미디어 설명")
    @Column(nullable = true, length = 255)
    private String description;
}
