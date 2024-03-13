package com.example.demo.src.user.model;

import com.example.demo.src.user.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;
import com.fasterxml.jackson.annotation.JsonProperty;

//카카오(서드파티)로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUser {
    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("kakao_account")
    private KaKaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Properties {
        @JsonProperty("nickname")
        public String nickName;
        @JsonProperty("profile_image")
        public String profileImageUrl;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaKaoAccount {
        @JsonProperty("email")
        public String email;
    }

    public User toEntity() {
        return User.builder()
                .email(this.kakaoAccount.email)
                .password("NONE")
                .userName(this.properties.nickName)
                .profileImageUrl(this.properties.profileImageUrl)
                .fullName(this.properties.nickName)
                .isOAuth(true)
                .userState(User.UserState.PENDING)
                .build();
    }

}

