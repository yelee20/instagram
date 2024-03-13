package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.utils.ValidationAnnotation;
import jdk.vm.ci.meta.Local;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "TERMS")
public class Terms extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "terms", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<TermsLog> termsLogs = new ArrayList<TermsLog>();

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRequired;

    @Column(nullable = false)
    private Integer version;

    @Builder
    public Terms(Long id, String title, String content, Boolean isRequired, Integer version
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isRequired = isRequired;
        this.version = version;
    }
}
