package com.chicchoc.sivillage.domain.unsignedMember.domain;

import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unsignedMember")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UnsignedMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unsigned_member_id")
    private Long id;

    @Column(nullable = false, length = 21)
    private String unsignedMemberUuid;

    @Column
    private LocalDateTime lastConnectedAt;
}
