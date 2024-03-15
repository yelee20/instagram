package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCollectionDto {
    private Long memoId;

    public Comment toEntity(User user) {
        return Comment.builder()
                .user(user)
                .build();
    }
}
