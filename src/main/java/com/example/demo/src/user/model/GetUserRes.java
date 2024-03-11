package com.example.demo.src.user.model;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private Long id;
    private String email;
    private String fullName;
    private String mobile;
    private String userName;
    private Boolean isOAuth;
    private String gender;
    private String profileImageUrl;
    private String website;
    private LocalDate birthday;
    private Boolean isPublic;
    private User.Role role;
    private User.UserState userState;
    protected BaseEntity.State state;

    public GetUserRes(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getFullName();
        this.mobile = user.getMobile();
        this.userName = user.getUserName();
        this.gender = user.getGender();
        this.profileImageUrl = user.getProfileImageUrl();
        this.website = user.getWebsite();
        this.birthday = user.getBirthday();
        this.isPublic = user.getIsPublic();
        this.userState = user.getUserState();
        this.state = user.getState();
    }
}
