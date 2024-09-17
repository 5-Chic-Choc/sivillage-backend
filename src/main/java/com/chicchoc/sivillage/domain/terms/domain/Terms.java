package com.chicchoc.sivillage.domain.terms.domain;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "terms_id")
    private Long id;

    @Comment("상위 약관 id")
    @Column(name = "parent_terms_id")
    private Long parent;

    @Comment("필수 여부")
    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Comment("약관 제목")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Comment("약관 내용")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Comment("view 여부")
    @Column(name = "isview", nullable = false)
    private Boolean isView;

    @Builder
    public Terms(Long parent, Boolean isRequired, String title, String content, Boolean isView) {
        this.parent = parent;
        this.isRequired = isRequired;
        this.title = title;
        this.content = content;
        this.isView = isView;
    }
}