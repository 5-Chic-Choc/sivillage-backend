package com.chicchoc.sivillage.domain.terms.domain;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.auth.dto.in.UserTermsRequestDto;
import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
public class UserTermsList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_terms_list_id")
    private Long id;

    @Comment("회원 uuid")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Comment("약관 id")
    @Column(nullable = false)
    private Long termsId;

    @Comment("동의 여부")
    @Column(nullable = false)
    private Boolean isAgree;

    @Builder
    public UserTermsList(Member member, Long termsId, Boolean isAgree) {
        this.member = member;
        this.termsId = termsId;
        this.isAgree = isAgree;
    }

    public static List<UserTermsList> of(Member member, List<UserTermsRequestDto> terms) {
        return terms.stream()
                .map(term -> UserTermsList.builder()
                        .member(member)
                        .termsId(term.getTermsId())
                        .isAgree(term.getIsAgree())
                        .build())
                .collect(Collectors.toList());
    }
}
