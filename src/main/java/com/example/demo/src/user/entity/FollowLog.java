package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@Entity
@Table(name = "FOLLOW_LOG")
public class FollowLog extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followId")
    private Follow follow;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetUserId")
    private User targetUser;

    @Column(nullable = false)
    private Boolean isCloseFriend;

    @Enumerated(EnumType.STRING)
    @Column(name = "followState", nullable = false, length = 10)
    private Follow.followState followState;


    @Builder
    public FollowLog(Long id, Follow follow, User user, User targetUser, Boolean isCloseFriend
    ) {
        this.id = id;
        this.follow = follow;
        this.user = user;
        this.targetUser = targetUser;
        this.isCloseFriend = isCloseFriend;

    }

}
