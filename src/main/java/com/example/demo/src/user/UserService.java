package com.example.demo.src.user;



import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;
import static com.example.demo.src.user.entity.User.UserState.PENDING;

// Service Create, Update, Delete 의 로직 처리
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    //POST
    public PostUserRes createUser(PostUserReq postUserReq) {
        //중복 체크
        Optional<User> checkUser = userRepository.findByEmailAndState(postUserReq.getEmail(), ACTIVE);
        if(checkUser.isPresent()){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        User saveUser = userRepository.save(postUserReq.toEntity());
        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserRes(saveUser.getId(), jwtToken);

    }

    public PostUserRes createOAuthUser(User user) {

        // User 추가 정보 저장
        User saveUser = userRepository.save(user);

        // JWT 발급
        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserRes(saveUser.getId(), jwtToken);

    }

    public void modifyUserName(Long userId, PatchUserReq patchUserReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateFullName(patchUserReq.getName());
    }
    public void modifyUserBasicInfo(Long userId, PostUserReq postUserReq) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String fullName = postUserReq.getFullName() != null ? postUserReq.getFullName() : user.getFullName();
        String userName = postUserReq.getUserName() != null ? postUserReq.getUserName() : user.getUserName();
        String mobile = postUserReq.getMobile() != null ? postUserReq.getMobile() : user.getMobile();
        LocalDate birthday = postUserReq.getBirthday() != null ? postUserReq.getBirthday() : user.getBirthday();
        String gender = postUserReq.getGender() != null ? postUserReq.getGender() : user.getGender();
        String profileImageUrl = postUserReq.getProfileImageUrl() != null ? postUserReq.getProfileImageUrl() : user.getProfileImageUrl();
        User.UserState userState = postUserReq.getUserState() != null ? postUserReq.getUserState() : user.getUserState();

        user.updateFullName(fullName);
        user.updateUserName(userName);
        user.updateMobile(mobile);
        user.updateBirthday(birthday);
        user.updateGender(gender);
        user.updateProfileImageUrl(profileImageUrl);
        user.updateUserState(userState);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.deleteUser();
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsers() {
        List<GetUserRes> getUserResList = userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    @Transactional(readOnly = true)
    public List<GetUserRes> getUsersByEmail(String email) {
        List<GetUserRes> getUserResList = userRepository.findAllByEmailAndState(email, ACTIVE).stream()
                .map(GetUserRes::new)
                .collect(Collectors.toList());
        return getUserResList;
    }


    @Transactional(readOnly = true)
    public GetUserRes getUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmailAndState(email, ACTIVE);
        if (result.isPresent()) return true;
        return false;
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) {
        User user = userRepository.findByEmailAndState(postLoginReq.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            Long userId = user.getId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginRes(userId,jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public GetUserRes getActiveUserById(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }

    public GetUserRes getUserByEmail(String email) {
        User user = userRepository.findByEmailAndState(email, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserRes(user);
    }
}
