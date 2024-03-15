package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.PostUserReq;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemoDto {

    @NotBlank(message = "메모를 입력해주세요.")
    @Size(max = 2200, message = "게시글은 2,200자를 초과할 수 없습니다.")
    private String memo;
    private String location;
    private Boolean isLikeCountVisible = true;
    private Boolean isCommentEnabled = true;

    public Memo toEntity(User user) {
        return Memo.builder()
                .memo(this.memo)
                .isLikeCountVisible(this.isLikeCountVisible)
                .isCommentEnabled(this.isCommentEnabled)
                .location(this.location)
                .user(user)
                .build();
    }

}
