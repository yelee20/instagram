package com.example.demo.src.user.model;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.user.entity.Terms;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetTermsRes {
    private Long id;
    private String title;
    private String content;
    private Boolean isRequired;
    private Integer version;

    public GetTermsRes(Terms terms) {
        this.id = terms.getId();
        this.title = terms.getTitle();
        this.content = terms.getContent();
        this.isRequired = terms.getIsRequired();
        this.version = terms.getVersion();
    }
}
