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
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;

    @Comment("사이즈 이름")
    @Column(nullable = false, length = 10, unique = true)
    private String name;
}
