package com.chicchoc.sivillage.domain.terms.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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

    @Enumerated(EnumType.STRING)
    @Comment("약관 종류")
    @Column(name = "type", nullable = false)
    private TermsType type;

    @Comment("view 여부")
    @Column(name = "isview", nullable = false)
    private Boolean isView;

    @Builder
    public Terms(Long parent, Boolean isRequired, String title, String content, TermsType type, Boolean isView) {
        this.parent = parent;
        this.isRequired = isRequired;
        this.title = title;
        this.content = content;
        this.type = type;
        this.isView = isView;
    }
}