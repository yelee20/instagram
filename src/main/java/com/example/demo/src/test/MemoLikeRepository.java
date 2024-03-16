package com.example.demo.src.test;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.test.entity.MemoLike;
import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike, Long> {
    Optional<MemoLike> findByMemoIdAndUserId(Long memoId, Long userId);
    Optional<MemoLike> findByMemoIdAndUserIdAndState(Long memoId, Long userId,  BaseEntity.State state);

    List<MemoLike> findAllByUserIdAndState(Long userId, BaseEntity.State state);
}
