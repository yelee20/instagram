package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.CollectionDetail;
import com.example.demo.src.test.entity.CollectionUser;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.utils.ValidationAnnotation;
import jdk.vm.ci.meta.Local;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "USER") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class User extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String mobile;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 20)
    private String userName;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isOAuth;

    @Column(length = 30)
    private String gender;

    @Column(length = 500)
    private String profileImageUrl;

    @Column(length = 500)
    private String website;

    @Column
    private LocalDate birthday;

    @Column
    @ColumnDefault("true")
    private Boolean isPublic;

    @Column(nullable = false)
    @ColumnDefault("BASIC")
    private User.Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("PENDING")
    private User.UserState userState;

    public enum Role {
        ADMIN, BASIC
    }

    public enum UserState {
        PENDING, ACTIVE, BLOCKED, DORMANT
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CollectionUser> collectionUsers = new ArrayList<CollectionUser>();

    @Builder
    public User(Long id, String email, String mobile, String password, String fullName,
                String userName, String gender, String profileImageUrl, String website, LocalDate birthday,
                UserState userState, Boolean isPublic, Boolean isOAuth
    ) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.fullName = fullName;
        this.userName = userName;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.website = website;
        this.birthday = birthday;
        this.userState = userState;
        this.isPublic = isPublic;
        this.isOAuth = isOAuth;
    }

    public void updateFullName(String fullName) {
        this.fullName = fullName;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }
    public void updateMobile(String mobile) {
        this.mobile = mobile;
    }
    public void updateBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public void updateGender(String gender) {
        this.gender = gender;
    }

    public void updateProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateUserState(UserState userState) {
        this.userState = userState;
    }

    public void updateState(State state) {
        this.state = state;
    }


    public void deleteUser() {
        this.state = State.INACTIVE;
    }

}
