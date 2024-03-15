package com.example.demo.src.test;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoImage;
import com.example.demo.src.test.entity.MemoLog;
import com.example.demo.src.test.model.*;
import com.example.demo.src.user.UserRepository;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.entity.TermsLog;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional
@Service
public class TestService {

    private final MemoRepository memoRepository;
    private final MemoLogRepository memoLogRepository;
    private final MemoImageRepository memoImageRepository;
    private final CommentRepository commentRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final S3UploadService s3UploadService;

    public void createMemo(MemoDto memoDto, List<MultipartFile> files) throws BaseException {

        User user = userService.getUserByJwt();

        Memo savedMemo = memoRepository.save(memoDto.toEntity(user));

        // save images
        for (MultipartFile file : files) {
            String fileType = file.getContentType();

            String fileUrl = s3UploadService.upload(file);
            MemoImageDto memoImageDto = new MemoImageDto();

            memoImageDto.setUrl(fileUrl);

            // Determine if the file is an image or video based on the content type
            if (fileType.startsWith("image")) {
                memoImageDto.setImageType(MemoImage.ImageType.PHOTO);
            } else if (fileType.startsWith("video")) {
                memoImageDto.setImageType(MemoImage.ImageType.VIDEO);
            }
            System.out.println(memoImageDto.toString());
            MemoImage memoImage = memoImageDto.toEntity(file, savedMemo);
            memoImageRepository.save(memoImage);
        }

        MemoLogDto memoLogDto = new MemoLogDto();
        MemoLog memoLog = memoLogDto.toEntity(savedMemo);
        memoLogRepository.save(memoLog);

    }


    @Transactional(readOnly = true)
    public int checkMemo(String memo){

        List<Memo> memoList = memoRepository.findByMemoAndState(memo, ACTIVE);
        return memoList.size();

    }

    @Transactional(readOnly = true)
    public List<GetMemoDto> getMemos(int startPage){

        // 페이징 예제
        PageRequest pageRequest = PageRequest.of(startPage, 5, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Memo> memoSlice = memoRepository.findAllByState(ACTIVE, pageRequest);
        Slice<GetMemoDto> getMemoDtoSlice = memoSlice.map(GetMemoDto::new);
        List<GetMemoDto> getMemoDtoList = getMemoDtoSlice.getContent();

        return getMemoDtoList;

    }

    public void modifyMemo(Long memoId, MemoDto memoDto) throws BaseException {
        //중복
        if(checkMemo(memoDto.getMemo()) >= 1){
            throw new BaseException(POST_TEST_EXISTS_MEMO);
        }
        Memo memo = memoRepository.findByIdAndState(memoId, ACTIVE)
                .orElseThrow(() -> new BaseException(MODIFY_FAIL_MEMO));

        memo.updateMemo(memoDto);

    }

    public void createComment(PostCommentDto postCommentDto){
        Memo memo = memoRepository.findByIdAndState(postCommentDto.getMemoId(), ACTIVE).
                orElseThrow(() -> new BaseException(INVALID_MEMO));

        User user = userService.getUserByJwt();
        commentRepository.save(postCommentDto.toEntity(memo, user));
    }
}
