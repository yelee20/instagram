package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.test.model.PatchMemoDto;
import com.example.demo.src.user.entity.User;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity // 필수, Class 를 Database Table화 해주는 것이다
@Table(name = "MEMO") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class Memo extends BaseEntity {

    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @NotNull
    private User user;

    @Column(name = "memo", nullable = false)
    @Size(max = 2200, message = "게시글 내용은 100자를 초과할 수 없습니다.")
    private String memo;

    @Column(nullable = false)
    private Boolean isCommentEnabled;

    @Column(nullable = false)
    private Boolean isLikeCountVisible;

    @Column(name = "location")
    @Size(max = 100, message = "위치는 100자를 초과할 수 없습니다.")
    private String location;

    // 양방향 매핑(선택 사항)
    @BatchSize(size = 5) // BatchSize 설정 예제
    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Comment> commentList = new ArrayList<Comment>();

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemoImage> memoImageList = new ArrayList<MemoImage>();

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemoLike> likes = new ArrayList<MemoLike>();

    @OneToMany(mappedBy = "memo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<CollectionDetail> collections = new ArrayList<CollectionDetail>();


    public void makeMemo(MemoDto memoDto) {
        this.memo = memoDto.getMemo();
    }

    public void updateMemoAdvancedSetting(PatchMemoDto memoDto) {
        if (memoDto.getIsCommentEnabled() != null) {
            this.isCommentEnabled = memoDto.getIsCommentEnabled();
        }
        if (memoDto.getIsLikeCountVisible() != null) {
            this.isLikeCountVisible = memoDto.getIsLikeCountVisible();
        }
    }
    public void updateMemo(PatchMemoDto memoDto) {
        this.memo = memoDto.getMemo();
        this.location = memoDto.getLocation();
    }

    public void deleteMemo() {
        this.state = State.INACTIVE;
    }

    // 연관관계 편의 메서드(선택 사항)
    public void addComment(Comment comment) {
        comment.setMemo(this);
        commentList.add(comment);
    }

    public boolean isLikedByUser(User user) {
        for (MemoLike like : likes) {
            if (like.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }
    public boolean isSavedByUser(User user) {
        for (CollectionDetail detail : collections) {
            if (detail.getCollection().getCollectionUsers().contains(user)) {
                return true;
            }
        }
        return false;
    }

    @Builder
    public Memo(Long id, User user, String memo, Boolean isCommentEnabled, Boolean isLikeCountVisible, String location) {
        this.id = id;
        this.user = user;
        this.memo = memo;
        this.isCommentEnabled = isCommentEnabled;
        this.isLikeCountVisible = isLikeCountVisible;
        this.location = location;
    }


}
