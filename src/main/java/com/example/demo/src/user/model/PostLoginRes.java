package com.example.demo.src.user.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginRes {

    private Long id;
    private String jwt;
}
