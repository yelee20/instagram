package com.example.demo.src.test;

import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.test.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.demo.common.response.BaseResponseStatus.*;


@Tag(name = "게시글 도메인", description = "메모 API, 코멘트 API") // swagger 접속: http://localhost:9000/swagger-ui/index.html
@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
public class TestController {

    private final TestService testService;
    private final S3UploadService s3UploadService;


    /**
     * 로그 테스트 API
     * [GET] /app/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String logTest() {
        System.out.println("테스트");
        return "Success Test";
    }

    /**
     * 메모 생성 API
     * [POST] /app/memos
     * @return BaseResponse<String>
     */
    // Body
    @Operation(summary = "메모 생성", description = "문자열과 사진/동영상 파일을 받아 메모를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "EMPTY_FILE_EXCEPTION"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @ResponseBody
    @PostMapping("/memos")
    public BaseResponse<String> createMemo(@Validated @ModelAttribute MemoDto memoDto,
                                           @Validated @RequestPart List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                throw new BaseException(EMPTY_FILE_EXCEPTION);
            }
        }
        testService.createMemo(memoDto, files);
        return new BaseResponse<>("게시글 생성 성공");
    }


    /**
     * 메모 리스트 조회 API
     * [GET] /app/memos
     * @return BaseResponse<List<TestDto>>
     */
    //Query String
    @Operation(summary = "메모 리스트 조회", description = "저장된 메모 리스트를 조회합니다.")
    @ResponseBody
    @GetMapping("/memos")
    public BaseResponse<List<GetMemoDto>> getMemos(@RequestParam(required = true) int startPage) {
        List<GetMemoDto> getMemoDtoList = testService.getMemos(startPage);
        return new BaseResponse<>(getMemoDtoList);
    }

    /**
     * 메모 추가 정보 변경 API
     * [PATCH] /app/memos/advanced/{memoId}
     * @return BaseResponse<String>
     */
    @Operation(summary = "메모 고급 설정 변경", description = "입력받은 메모 id의 메모의 좋아요 수 표시 및 댓글 기능 해제 여부를 변경합니다.")
    @ResponseBody
    @PatchMapping("/memos/advanced/{memoId}")
    public BaseResponse<String> modifyMemoAdvancedSetting(@PathVariable("memoId") Long memoId,
                                                          @Validated @RequestBody PatchMemoDto memoDto) {
        testService.modifyMemoAdvancedSetting(memoId, memoDto);

        String result = "메모 고급 설정 수정 성공";
        return new BaseResponse<>(result);

    }


    /**
     * 메모 기본 정보 변경 API
     * [PATCH] /app/memos/{memoId}
     * @return BaseResponse<String>
     */
    @Operation(summary = "메모 기본 정보 변경", description = "입력받은 메모 id의 메모를 받아온 문자열 값으로 변경합니다.")
    @ResponseBody
    @PatchMapping("/memos/{memoId}")
    public BaseResponse<String> modifyMemo(@PathVariable("memoId") Long memoId,
                                           @Validated @RequestBody PatchMemoDto memoDto) {
        testService.modifyMemo(memoId, memoDto);

        String result = "수정 성공!!";
        return new BaseResponse<>(result);

    }


    /**
     * 메모 삭제 API
     * [DELETE] /app/memos/{memoId}
     * @return BaseResponse<String>
     */
    @Operation(summary = "메모 삭제", description = "입력받은 메모 id를 삭제합니다.")
    @ResponseBody
    @DeleteMapping("/memos/{memoId}")
    public BaseResponse<String> deleteMemo(@PathVariable("memoId") Long memoId) {
        testService.deleteMemo(memoId);

        String result = "게시글 삭제 성공";
        return new BaseResponse<>(result);
    }


    /**
     * 코멘트 생성 API
     * [POST] /app/comments
     * @return BaseResponse<String>
     */
    // Body
    @Operation(summary = "코멘트 생성", description = "입력 받은 메모에 받아온 문자열로 코멘트를 생성합니다.")
    @ResponseBody
    @PostMapping("/comments")
    public BaseResponse<String> createComment(@Validated @RequestBody PostCommentDto postCommentDto) {
        testService.createComment(postCommentDto);
        return new BaseResponse<>("성공");
    }

    /**
     * 코멘트 삭제 API
     * [DELETE] /app/comments/{commentId}
     * @return BaseResponse<String>
     */
    @Operation(summary = "코멘트 삭제", description = "입력 받은 메모에 받아온 문자열로 코멘트를 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "INVALID REQUEST"),
            @ApiResponse(responseCode = "401", description = "댓글 삭제 권한 없음"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @ResponseBody
    @DeleteMapping("/comment/{commentId}")
    public BaseResponse<String> deleteComment(@Validated @PathVariable("commentId") Long commentId) {
        testService.deleteComment(commentId);
        return new BaseResponse<>("댓글 삭제 성공");
    }
}
