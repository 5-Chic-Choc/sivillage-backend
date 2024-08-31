package com.chicchoc.sivillage.domain.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Comment("상위 카테고리 ID")
    @Column(nullable = true)
    private Long parent_id;

    @Comment("카테고리 이름")
    @Column(nullable = false, length = 30)
    private String name;

    @Comment("카테고리 경로")
    @Column(nullable = false, length = 255)
    private String path;
}
