package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "STORY")
public class Story extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false, updatable = false)
    private String contentUrl;

    @Column(nullable = false, updatable = false)
    private ContentType contentType;

    @Column(nullable = false, updatable = false)
    @ColumnDefault("false")
    private Boolean isCloseFriendOnly;

    public enum ContentType {
        VIDEO, PHOTO
    }

    @Builder
    public Story(Long id, User user, String contentUrl, ContentType contentType, Boolean isCloseFriendOnly) {
        this.id = id;
        this.user = user;
        this.contentUrl = contentUrl;
        this.contentType = contentType;
        this.isCloseFriendOnly = isCloseFriendOnly;
    }

    public void deleteStory() {
        this.state = State.INACTIVE;
    }

}
