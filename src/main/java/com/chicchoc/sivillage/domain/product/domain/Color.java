package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long id;

    @Comment("색상 필터링 이름")
    @Column(nullable = false, length = 10, unique = true)
    private String name;

    @Comment("색상 상세 값")
    @Column(nullable = false, length = 30)
    private String value;
}
