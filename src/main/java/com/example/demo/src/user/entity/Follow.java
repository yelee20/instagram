package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.src.user.entity.Follow.followState.ACCEPTED;
import static com.example.demo.src.user.entity.Follow.followState.PENDING;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Entity
@Table(name = "FOLLOW")
public class Follow extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetUserId")
    private User targetUser;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isCloseFriend;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACCEPTED")
    @Column(name = "followState", nullable = false, length = 10)
    private Follow.followState followState;

    public enum followState {
        ACCEPTED, PENDING, REJECTED;
    }

    @Builder
    public Follow(Long id, User user, User targetUser, Boolean isCloseFriend, Follow.followState followState
    ) {
        this.id = id;
        this.user = user;
        this.targetUser = targetUser;
        this.isCloseFriend = isCloseFriend;
        this.followState = followState;

    }

    public static Follow create() {
        return new Follow();
    }

    public void createFollowRequest(User user) {
        System.out.println(user.getId());
        if (user.getIsPublic()) {
            this.followState = ACCEPTED;
        } else {
            this.followState = PENDING;
        }
        this.state = ACTIVE;

    }
    public void deleteFollow() {
        this.state = State.INACTIVE;
    }

    public FollowLog createFollowLog(User user, User targetUser) {
        FollowLog followLog = new FollowLog();
        followLog.setFollow(this);
        followLog.setUser(user);
        followLog.setTargetUser(targetUser);
        followLog.setIsCloseFriend(this.isCloseFriend);
        followLog.setFollowState(this.followState);
        return followLog;
    }

}
