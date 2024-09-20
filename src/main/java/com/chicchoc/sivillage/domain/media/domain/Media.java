package com.chicchoc.sivillage.domain.media.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id")
    private Long id;

    @Comment("미디어 URL")
    @Column(nullable = false, length = 2000)
    private String mediaUrl;

    @Comment("미디어 종류")
    @Column(nullable = false, length = 20)
    private String mediaType;

    @Comment("미디어 설명")
    @Column(nullable = true, length = 255)
    private String description;

    @Builder
    public Media(String mediaUrl, String mediaType, String description) {
        this.mediaUrl = mediaUrl;
        this.mediaType = mediaType;
        this.description = description;
    }
}
