package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "color_id")
    private Long id;

    @Comment("색상 필터링 이름")
    @Column(nullable = false, length = 10)
    private String name;

    @Comment("색상 상세 값")
    @Column(nullable = false, length = 30)
    private String value;
}
