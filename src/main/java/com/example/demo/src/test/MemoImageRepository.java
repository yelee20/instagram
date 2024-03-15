package com.example.demo.src.test;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.MemoImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoImageRepository extends JpaRepository<MemoImage, Long> {
    Optional<MemoImage> findByIdAndState(Long memoImageId, BaseEntity.State state);
}
