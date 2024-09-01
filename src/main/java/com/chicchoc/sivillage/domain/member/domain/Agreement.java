package com.chicchoc.sivillage.domain.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "agreement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Comment("필수 여부")
    @Column(nullable = false)
    private boolean isRequired;

    @Comment("약관 제목")
    @Column(nullable = false, length = 30)
    private String title;

    @Comment("약관 내용")
    @Column(nullable = false, length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Agreement parent;

    @OneToMany(mappedBy = "parent")
    private List<Agreement> child = new ArrayList<>();
}
