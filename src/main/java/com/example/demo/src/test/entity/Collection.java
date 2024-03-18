package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.CollectionDto;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "COLLECTION")
public class Collection extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnailMemoId")
    private Memo thumbnailMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdUser", nullable = false)
    private User createdUser;

    @Column(nullable = false)
    private Boolean isPublic;

    @OneToMany(mappedBy = "collection")
    private List<CollectionUser> collectionUsers = new ArrayList<CollectionUser>();

    @OneToMany(mappedBy = "collection")
    private List<CollectionDetail> collectionDetails = new ArrayList<CollectionDetail>();

    public boolean isAddedToUserCollection(User user, Memo memo) {
        for (CollectionDetail detail : collectionDetails) {
            if (detail.getCollection().equals(this) && detail.getMemo().equals(memo)) {
                return true;
            }
        }
        return false;
    }

    @Builder
    public Collection(Long id, String name, User createdUser, Memo thumbnailMemo, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.createdUser = createdUser;
        this.thumbnailMemo = thumbnailMemo;
        this.isPublic = isPublic;
    }


}
