package com.example.demo.src.test;


import com.example.demo.src.test.entity.MemoImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoImageRepository extends JpaRepository<MemoImage, Long> {
}
