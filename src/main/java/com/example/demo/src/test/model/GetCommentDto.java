package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.CommentLike;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.utils.Formats.formatTimeToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentDto {

    private Long commentId;
    private Long parentCommentId;
    private Long userId;
    private String userName;
    private String profileImageUrl;
//    private Boolean postedStoryToday;
    private String commentContent;
    private Integer likeNum;
    private Boolean isLikedByMe;
    private String createdAt;


    public GetCommentDto(Comment comment, User user) {
//    private Boolean postedStoryToday;
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.userName = comment.getUser().getUserName();
        this.commentContent = comment.getComment();
        this.profileImageUrl = comment.getUser().getProfileImageUrl();
        this.likeNum = comment.getLikes().size();
        this.isLikedByMe = comment.isLikedByUser(user);
        this.createdAt = formatTimeToString(comment.getCreatedAt());

    }
}
