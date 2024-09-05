package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;

    @Comment("사이즈 값")
    @Column(nullable = false, length = 10)
    private String value;
}
