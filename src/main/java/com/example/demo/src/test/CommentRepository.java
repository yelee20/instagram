package com.example.demo.src.test;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndState(Long commentId, BaseEntity.State state);
    Optional<Comment> findByMemoIdAndState(Long memoId, BaseEntity.State state);
    Optional<Comment> findByIdAndMemoIdAndState(Long commentId, Long memoId, BaseEntity.State state);
    List<Comment> findAllByParentCommentIdAndState(Long commentId, BaseEntity.State state);
}
