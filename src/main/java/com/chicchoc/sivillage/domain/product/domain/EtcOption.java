package com.chicchoc.sivillage.domain.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
public class EtcOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "etc_option_id")
    private Long id;

    @Comment("기타 옵션 값")
    @Column(nullable = false, length = 100)
    private String value;
}
