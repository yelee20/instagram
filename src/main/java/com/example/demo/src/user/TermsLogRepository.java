package com.example.demo.src.user;

import com.example.demo.src.user.entity.Terms;
import com.example.demo.src.user.entity.TermsLog;
import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.State;

public interface TermsLogRepository extends JpaRepository<TermsLog, Long> {
}
