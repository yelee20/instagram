package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "COMMENT") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Comment extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memoId")
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId")
    private Comment parentComment;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CommentLike> likes = new ArrayList<CommentLike>();

    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    public boolean isLikedByUser(User user) {
        for (CommentLike like : likes) {
            if (like.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Builder
    public Comment(Long id, User user, String comment, Memo memo, Comment parentComment) {
        this.id = id;
        this.user = user;
        this.comment = comment;
        this.parentComment = parentComment;
        this.memo = memo;
    }

    public void makeComment(PostCommentDto postCommentDto, Memo memo, Comment parentComment) {
        this.memo = memo;
        this.comment = postCommentDto.getComment();
        this.parentComment = parentComment;
    }

    public void deleteComment() {
        this.state = State.INACTIVE;
    }

}
