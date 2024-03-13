package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.Memo;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@ToString
@Table(name = "TERMS_LOG")
public class TermsLog extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "termsId")
    private Terms terms;

    @Column(nullable = false)
    private Integer termsVersion = 1;

    @Column(nullable = false)
    private Boolean hasAgreed;

    @Builder
    public TermsLog(Long id, User user, Terms terms, Boolean hasAgreed, Integer termsVersion
    ) {
        this.id = id;
        this.user = user;
        this.terms = terms;
        this.termsVersion = termsVersion;
        this.hasAgreed = hasAgreed;
    }

    public static TermsLog create() {
        return new TermsLog();
    }
}
