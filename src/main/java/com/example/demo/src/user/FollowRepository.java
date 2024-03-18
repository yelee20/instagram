package com.example.demo.src.user;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByTargetUserIdAndUserId(Long targetUserId, Long userId);
    Optional<Follow> findByTargetUserIdAndUserIdAndState(Long targetUserId, Long userId,  BaseEntity.State state);

    Optional<Follow> findByTargetUserIdAndUserIdAndFollowStateAndState(Long targetUserId,
                                                                       Long userId,
                                                                       Follow.followState followState,
                                                                       BaseEntity.State state);

    List<Follow> findAllByUserIdAndState(Long userId, BaseEntity.State state);
    Slice<Follow> findAllByUserIdAndFollowStateAndState(Long userId, Follow.followState followState, BaseEntity.State state, Pageable pageable);
}
