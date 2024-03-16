package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatchMemoDto {
    @Size(max = 2200, message = "게시글은 2,200자를 초과할 수 없습니다.")
    private String memo;
    @Size(max = 100, message = "위치는 100자를 초과할 수 없습니다.")
    private String location;
    private Boolean isLikeCountVisible = true;
    private Boolean isCommentEnabled = true;
    private List<MemoImageDto> images;

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
