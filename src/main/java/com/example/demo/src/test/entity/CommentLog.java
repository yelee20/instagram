package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "COMMENT_LOG")
public class CommentLog extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoId")
    private Memo memo;
    @Builder
    public CommentLog(Long id, User user, Comment comment, String commentContent, Memo memo) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.commentContent = commentContent;
        this.memo = memo;
    }


}
