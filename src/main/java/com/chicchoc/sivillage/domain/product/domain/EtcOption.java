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
public class EtcOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_option_id")
    private Long id;

    @Comment("기타 옵션 이름")
    @Column(nullable = false, length = 100)
    private String name;
}
