package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.test.entity.MemoLike;
import com.example.demo.src.user.entity.User;
import com.example.demo.utils.Formats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

import static com.example.demo.utils.Formats.formatTimeToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMemoDto {
    private Long memoId;
    private Long userId;
    private String userName;
    private String profileImageUrl;
    private Boolean postedStoryToday;
    private String memoContent;
    private List<Map<String, Object>> memoImageList = new ArrayList<Map<String, Object>>();
    private Integer memoImageNum;
    private List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
    private Integer memoCommentNum;
    private Integer likeNum;
    private String location;
    private String createdAt;
    private Boolean isSavedByMe;
    private Boolean isLikedByMe;
    private Boolean isCommentEnabled;
    private Boolean isLikeCountVisible;


    public GetMemoDto(Memo memo, User user) {
//    private Boolean postedStoryToday;
        this.memoId = memo.getId();
        this.userId = memo.getUser().getId();
        this.userName = memo.getUser().getUserName();
        this.memoContent = memo.getMemo();
        this.profileImageUrl = memo.getUser().getProfileImageUrl();
        this.location = memo.getLocation();
        this.isCommentEnabled = memo.getIsCommentEnabled();
        this.isLikeCountVisible = memo.getIsLikeCountVisible();
        this.createdAt = formatTimeToString(memo.getCreatedAt());


        for(Comment comment : memo.getCommentList()) {
            if (!memo.getIsCommentEnabled()) {
                break;
            }
            Map<String, Object> commentDict = new HashMap<>();
            commentDict.put("commentId", comment.getId());
            commentDict.put("parentCommentId", (comment.getParentComment() != null) ? comment.getParentComment().getId() : null);
            commentDict.put("userId", comment.getUser().getId());
            commentDict.put("userName", comment.getUser().getUserName());
            commentDict.put("userProfileImageUrl", comment.getUser().getProfileImageUrl());
            commentDict.put("commentContent", comment.getComment());
            commentDict.put("createdAt", formatTimeToString(comment.getCreatedAt()));
            commentList.add(commentDict);

            if (commentList.size() == 2) {
                break;
            }
        }

        for(MemoImage memoImage : memo.getMemoImageList()) {
            Map<String, Object> memoImageDict = new HashMap<>();
            memoImageDict.put("memoImageId", memoImage.getId());
            memoImageDict.put("memoImageUrl", memoImage.getUrl());
            memoImageDict.put("contentType", memoImage.getImageType());
            memoImageDict.put("altText", memoImage.getAltText());
            memoImageDict.put("length", memoImage.getLength());
            memoImageList.add(memoImageDict);
        }

        this.memoCommentNum =  (memo.getIsCommentEnabled()) ? memo.getCommentList().size() : null;
        this.memoImageNum = memo.getMemoImageList().size();
        this.likeNum = (memo.getIsLikeCountVisible()) ? memo.getLikes().size() : null;
        this.isLikedByMe = memo.isLikedByUser(user);
        this.isSavedByMe = memo.isSavedByUser(user);

    }
}
