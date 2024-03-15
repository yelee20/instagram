package com.example.demo.src.test;


import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.State;

public interface MemoLogRepository extends JpaRepository<MemoLog, Long> {
}
