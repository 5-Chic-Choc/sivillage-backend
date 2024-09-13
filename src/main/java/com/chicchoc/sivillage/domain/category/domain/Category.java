package com.chicchoc.sivillage.domain.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Comment("카테고리 이름")
    @Column(nullable = false, length = 30)
    private String name;

    @Comment("깊이")
    @Column(nullable = false)
    private Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 카테고리의 자식 목록 확인 가능, NPE 방지를 위해 ArrayList 사용
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        // 현재 카테고리의 자식 리스트에 새 자식 카테고리 추가
        this.child.add(child);
        // 새 자식 카테고리의 부모로 현재 카테고리 설정
        child.setParent(this);
    }
}
