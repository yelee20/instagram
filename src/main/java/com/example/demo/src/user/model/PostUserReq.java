package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.User;
import lombok.*;
import org.intellij.lang.annotations.RegExp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.PrePersist;
import javax.validation.constraints.*;
import java.time.LocalDate;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostUserReq {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 20, message = "이름은 20자를 초과할 수 없습니다.")
    private String fullName;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9._]{1,20}$", message = "사용자 이름에는 문자 숫자 밑줄 및 마침표만 사용할 수 있습니다.")
    private String userName;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^0\\d{1,2}(|\\))\\d{3,4}\\d{4}$",
            message = "전화번호 형식을 확인해주세요.")
    private String mobile;

    private LocalDate birthday;
    private String gender;
    private String profileImageUrl;
    private Boolean isOAuth;
    private User.UserState userState = User.UserState.ACTIVE;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .fullName(this.fullName)
                .userName(this.userName)
                .mobile(this.mobile)
                .birthday(this.birthday)
                .gender(this.gender)
                .profileImageUrl(this.profileImageUrl)
                .isOAuth(this.isOAuth)
                .isPublic(true)
                .build();
    }
}
