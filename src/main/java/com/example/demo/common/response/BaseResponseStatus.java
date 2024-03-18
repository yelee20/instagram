package com.example.demo.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request, Response 오류
     */

    INVALID_REQUEST_FORMAT(false, HttpStatus.BAD_REQUEST.value(), "잘못된 요청 형식입니다."),
    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    USERS_EMPTY_FULL_NAME(false, HttpStatus.BAD_REQUEST.value(), "Full Name을 입력해주세요."),
    USERS_EMPTY_USER_NAME(false, HttpStatus.BAD_REQUEST.value(), "User Name을 입력해주세요."),
    USERS_EMPTY_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호를 입력해주세요."),
    USERS_EMPTY_MOBILE(false, HttpStatus.BAD_REQUEST.value(), "전화번호를 입력해주세요."),
    USERS_EMPTY_BIRTHDAY(false, HttpStatus.BAD_REQUEST.value(), "생년월일을 입력해주세요."),
    EMPTY_FILE_EXCEPTION(false, HttpStatus.BAD_REQUEST.value(), "파일을 첨부해주세요."),
    EMPTY_IMAGE_ID_EXCEPTION(false,HttpStatus.NOT_FOUND.value(),"이미지 아이디를 입력해주세요."),
    NO_FILE_EXTENTION(false, HttpStatus.BAD_REQUEST.value(), "파일 확장자를 입력해주세요."),

    EMPTY_MEMO(false, HttpStatus.BAD_REQUEST.value(), "게시글 내용을 입력해주세요."),
    TEST_EMPTY_COMMENT(false, HttpStatus.BAD_REQUEST.value(), "코멘트를 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_MINIMUM_AGE(false, HttpStatus.BAD_REQUEST.value(), "만 8세 미만은 가입할 수 없습니다."),
    POST_USERS_EXCEEDS_MAX_LEN_FULL_NAME(false, HttpStatus.BAD_REQUEST.value(), "이름은 20자를 초과할 수 없습니다."),
    POST_USERS_RECEDES_MIN_LEN_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "비밀번호는 6자리 이상이어야 합니다."),
    POST_USERS_INVALID_USER_NAME(false, HttpStatus.BAD_REQUEST.value(), "사용자 이름에는 문자 숫자 밑줄 및 마침표만 사용할 수 있습니다."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),
    POST_USERS_EXISTS_ID(false,HttpStatus.BAD_REQUEST.value(),"사용할 수 없는 사용자 이름입니다. 다른 이름을 사용하세요."),
    POST_TEST_EXISTS_MEMO(false,HttpStatus.BAD_REQUEST.value(),"중복된 메모입니다."),

    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    DUPLICATED_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "중복된 이메일입니다."),
    INVALID_MEMO(false,HttpStatus.NOT_FOUND.value(), "존재하지 않는 메모입니다."),
    INVALID_COMMENT(false,HttpStatus.NOT_FOUND.value(), "존재하지 않는 댓글입니다."),
    COMMENT_IS_DISABLED(false,HttpStatus.NOT_FOUND.value(), "댓글 기능이 해제된 게시글입니다."),

    EXISTING_USER(false,HttpStatus.BAD_REQUEST.value(),"이미 존재하는 사용자입니다."),
    PENDING_USER(false,HttpStatus.BAD_REQUEST.value(),"회원 가입이 완료되지 않은 사용자입니다."),
    BLOCKED_USER(false,HttpStatus.BAD_REQUEST.value(),"차단된 사용자입니다."),
    DORMANT_USER(false,HttpStatus.BAD_REQUEST.value(),"휴면 계정입니다."),
    CANNOT_FOLLOW_MY_ACCOUNT(false,HttpStatus.BAD_REQUEST.value(),"같은 계정은 팔로우 할 수 없습니다."),
    FOLLOW_REQUEST_EXIST(false,HttpStatus.BAD_REQUEST.value(),"팔로우 요청 승인 대기 중인 계정입니다."),

    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"없는 아이디거나 비밀번호가 틀렸습니다."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    NOT_FIND_USER(false,HttpStatus.NOT_FOUND.value(),"일치하는 유저가 없습니다."),
    IMAGE_NOT_FOUND(false,HttpStatus.NOT_FOUND.value(),"일치하는 이미지가 없습니다."),
    MEMO_NOT_FOUND(false,HttpStatus.NOT_FOUND.value(),"존재하는 게시글이 없습니다."),
    COMMENT_NOT_FOUND(false,HttpStatus.NOT_FOUND.value(),"존재하는 댓글이 없습니다."),
    NOT_FOLLOWING(false,HttpStatus.NOT_FOUND.value(),"팔로우 하지 않는 유저입니다."),
    OAUTH_USER(false, HttpStatus.BAD_REQUEST.value(), "소셜 로그인 유저입니다. 카카오 로그인 버튼을 이용하여 로그인 해주세요"),
    INVALID_OAUTH_TYPE(false, HttpStatus.BAD_REQUEST.value(), "알 수 없는 소셜 로그인 형식입니다."),

    MISSING_REQUIRED_TERMS(false, HttpStatus.BAD_REQUEST.value(), "모든 필수 이용 약관에 동의해주세요."),
    INVALID_REQUIRED_TERMS(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 이용 약관입니다."),

    INVALID_FILE_EXTENTION(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 파일 형식입니다."),

    /**
     * 401 :  권한 없음 오류
     */
    NOT_ENOUGH_PERMISSION_EDIT_MEMO(false, HttpStatus.UNAUTHORIZED.value(), "게시글 수정 권한이 없습니다."),
    NOT_ENOUGH_PERMISSION_TO_ACCESS_MEMO(false, HttpStatus.UNAUTHORIZED.value(), "게시글 접근 권한이 없습니다."),
    NOT_ENOUGH_PERMISSION_DELETE_COMMENT(false, HttpStatus.UNAUTHORIZED.value(), "댓글 삭제 권한이 없습니다."),



    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),

    NO_SUCH_FIELD(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "해당되는 컬럼이 없습니다."),

    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"이미지 업로드 실패"),
    IO_EXCEPTION_ON_VIDEO_UPLOAD(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"동영상 업로드 실패"),
    IO_EXCEPTION_ON_IMAGE_DELETE(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"이미지 삭제 실패"),
    PUT_OBJECT_EXCEPTION(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"이미지 수정 실패"),

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");


    private boolean isSuccess;
    private int code;
    private String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
