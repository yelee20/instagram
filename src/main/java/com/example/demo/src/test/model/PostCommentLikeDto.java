package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.CommentLike;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentLikeDto {
    private Long commentId;
    private User user;

    public CommentLike toEntity(Comment comment) {
        return CommentLike.builder()
                .comment(comment)
                .user(this.user)
                .build();
    }
}
