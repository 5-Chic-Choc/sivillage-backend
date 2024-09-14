package com.chicchoc.sivillage.domain.oauth.domain;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_oauth_connection",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"oauth_id", "oauth_provider"})
        })
public class OauthMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_member_id")
    private long id;

    @Comment("회원 id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Comment("oauth 제공사")
    @Column(nullable = false, length = 20)
    private String oauthProvider;

    @Comment("oauth 회원 ID")
    @Column(nullable = false)
    private String oauthId;

    @Comment("oauth 회원 이메일")
    @Column(nullable = true)
    private String oauthEmail;

    public OauthMember(String oauthId, String oauthProvider, String oauthEmail, Member member) {
        super();
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.oauthEmail = oauthEmail;
        this.member = member;

    }
}
