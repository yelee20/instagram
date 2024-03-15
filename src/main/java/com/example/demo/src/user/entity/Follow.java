package com.example.demo.src.user.entity;

import com.example.demo.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
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
    private Boolean isCloseFriend;

    @Builder
    public Follow(Long id, User user, User targetUser, Boolean isCloseFriend
    ) {
        this.id = id;
        this.user = user;
        this.targetUser = targetUser;
        this.isCloseFriend = isCloseFriend;

    }

    public void updateState(State state) {
        this.state = state;
    }

    public void unFollow() {
        this.state = State.INACTIVE;
    }

}
