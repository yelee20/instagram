package com.example.demo.src.user;


import com.example.demo.src.user.entity.FollowLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowLogRepository extends JpaRepository<FollowLog, Long> {
}
