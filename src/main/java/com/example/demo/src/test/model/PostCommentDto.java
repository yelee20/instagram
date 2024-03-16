package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentDto {
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    private Long memoId;
    @NotBlank(message = "코멘트를 입력해주세요.")
    @Size(max = 1000, message = "코멘트는 1,000자를 넘을 수 없습니다.")
    private String comment;
    private Long parentCommentId;

    public Comment toEntity(Memo memo, User user, Comment parentComment) {
        return Comment.builder()
                .comment(this.comment)
                .user(user)
                .parentComment(parentComment)
                .memo(memo)
                .build();
    }
}
