package com.example.demo.src.test.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.model.CollectionDto;
import com.example.demo.src.test.model.PostCommentDto;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "COLLECTION_USER")
public class CollectionUser extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collectionId")
    private Collection collection;

    @Column(name = "role", nullable = false)
    private Role role;

    public enum Role {
        OWNER, GUEST
    }

    @Builder
    public CollectionUser(Long id, User user, Collection collection, Role role) {
        this.id = id;
        this.user = user;
        this.collection = collection;
        this.role = role;
    }

}
