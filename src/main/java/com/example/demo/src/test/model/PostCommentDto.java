package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
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
public class PostCommentDto {
    private Long memoId;
    @NotBlank(message = "코멘트를 입력해주세요.")
    private String comment;

    public Comment toEntity(Memo memo, User user) {
        return Comment.builder()
                .comment(this.comment)
                .user(user)
                .memo(memo)
                .build();
    }
}
