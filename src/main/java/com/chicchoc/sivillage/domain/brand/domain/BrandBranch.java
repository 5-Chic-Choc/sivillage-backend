package com.chicchoc.sivillage.domain.brand.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class BrandBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_branch_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Comment("브랜드 지사 이름")
    @Column(nullable = false, length = 255)
    private String name;
}
