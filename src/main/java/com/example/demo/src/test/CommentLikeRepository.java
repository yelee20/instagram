package com.example.demo.src.test;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    Optional<CommentLike> findByCommentIdAndUserIdAndState(Long commentId, Long userId,  BaseEntity.State state);

    List<CommentLike> findAllByUserIdAndState(Long userId, BaseEntity.State state);
}
