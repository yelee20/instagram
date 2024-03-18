package com.example.demo.src.user.model;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.Follow;
import com.example.demo.src.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetFollowingUserRes {

    private Long userId;
    private Long targetUserId;
    private String targetUserName;
    private String targetUserFullName;
    private String profileImageUrl;
    private Boolean postedStoryToday;
    private Boolean isCloseFriend;
    private Boolean isFollowedByMe;

    public GetFollowingUserRes(User user, User targetUser, Follow follow) {


        this.userId = user.getId();
        this.targetUserId = targetUser.getId();
        this.targetUserName = targetUser.getUserName();
        this.targetUserFullName = targetUser.getFullName();
        this.profileImageUrl = user.getMobile();
//        this.postedStoryToday = user.getIsPublic();

        this.isCloseFriend = follow.getIsCloseFriend();
        this.isFollowedByMe = true;
    }

}
