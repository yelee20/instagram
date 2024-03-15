package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.user.entity.User;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemoImageDto {

    private Long id;
    private String url;
    private String altText;
    private MemoImage.ImageType imageType;
    private Long length;

    public MemoImage toEntity(MultipartFile file, Memo memo) {
        return MemoImage.builder()
                .memo(memo)
                .url(this.url)
                .altText(file.getOriginalFilename())
                .length(file.getSize())
                .imageType(this.imageType)
                .build();
    }

}
