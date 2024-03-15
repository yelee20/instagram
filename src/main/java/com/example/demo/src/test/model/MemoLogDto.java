package com.example.demo.src.test.model;

import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.test.entity.MemoLog;
import com.example.demo.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemoLogDto {

    public MemoLog toEntity(Memo memo) {
        return MemoLog.builder()
                .memo(memo)
                .memoContent(memo.getMemo())
                .isLikeCountVisible(memo.getIsLikeCountVisible())
                .isCommentEnabled(memo.getIsCommentEnabled())
                .location(memo.getLocation())
                .user(memo.getUser())
                .build();
    }

}
