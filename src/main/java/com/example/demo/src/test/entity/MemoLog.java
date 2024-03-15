package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "MEMO_LOG")
public class MemoLog extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoId")
    private Memo memo;

    @Column(name = "memo", nullable = false)
    private String memoContent;

    @Column(nullable = false)
    private Boolean isCommentEnabled;

    @Column(nullable = false)
    private Boolean isLikeCountVisible;

    @Column(name = "location")
    private String location;

    @Builder
    public MemoLog(Long id, User user, Memo memo, String memoContent,
                   Boolean isCommentEnabled, Boolean isLikeCountVisible, String location) {
        this.id = id;
        this.user = user;
        this.memo = memo;
        this.memoContent = memoContent;
        this.isCommentEnabled = isCommentEnabled;
        this.isLikeCountVisible = isLikeCountVisible;
        this.location = location;
    }


}
