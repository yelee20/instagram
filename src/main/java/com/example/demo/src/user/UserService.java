package com.example.demo.src.user;



import com.example.demo.common.entity.BaseEntity;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.common.response.BaseResponse;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.MemoLike;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.user.entity.Follow;
import com.example.demo.src.user.entity.FollowLog;
import com.example.demo.src.user.entity.TermsLog;
import com.example.demo.src.user.entity.User;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.common.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.common.response.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TermsRepository termsRepository;
    private final TermsLogRepository termsLogRepository;
    private final FollowRepository followRepository;
    private final FollowLogRepository followLogRepository;
    private final TermsService termsService;
    private final JwtService jwtService;

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) {

        if (!postUserReq.getIsOAuth()) {
            // 중복 체크
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
        }

        checkIfRequiredTermsExists(postUserReq);

        postUserReq.setUserState(User.UserState.ACTIVE);

        User savedUser;
        if (postUserReq.getIsOAuth()) {

            Long jwtUserId = jwtService.getUserId();
            postUserReq.setPassword("NONE");
            modifyUserBasicInfo(jwtUserId, postUserReq);

            savedUser = userRepository.findByIdAndState(jwtUserId, ACTIVE)
                    .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        } else {
            savedUser = userRepository.save(postUserReq.toEntity());
        }
        List<TermsLog> termsLogs = convertToTermsLogs(savedUser, postUserReq.getTerms());

        termsLogRepository.saveAll(termsLogs);
        String jwtToken = jwtService.createJwt(savedUser.getId());
        return new PostUserRes(savedUser.getId(), jwtToken);

    }

    private void checkIfRequiredTermsExists(PostUserReq postUserReq) {

        // 필수 이용 약관 동의 여부 확인
        List<GetTermsRes> requiredTermsList = termsService.getRequiredTerms();
        List<PostUserReq.Terms> submittedTermsList = postUserReq.getTerms();

        for (GetTermsRes requiredTerm : requiredTermsList) {
            boolean found = false;

            for (PostUserReq.Terms submittedTerm : submittedTermsList) {
                if (Objects.equals(requiredTerm.getId(), submittedTerm.getTermsId()) &&
                        submittedTerm.getHasAgreed()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Handle the case where a required term is missing or not agreed to
                throw new BaseException(MISSING_REQUIRED_TERMS);
            }
        }

        for (PostUserReq.Terms submittedTerm : submittedTermsList) {
            if (!termsService.checkTermsById(submittedTerm.getTermsId())) {
                throw new BaseException(INVALID_REQUIRED_TERMS);
            }
        }
    }

    private List<TermsLog> convertToTermsLogs(User user, List<PostUserReq.Terms> terms) {
        return terms.stream()
                .map(term -> {
                    TermsLog termsLog = TermsLog.create();
                    termsLog.setUser(user);
                    termsLog.setTerms(termsRepository.findByIdAndState(term.getTermsId(), ACTIVE).orElse(null));
                    termsLog.setHasAgreed(term.getHasAgreed());
                    return termsLog;
                })
                .collect(Collectors.toList());
    }

    public PostUserRes createOAuthUser(User user) {
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

        if (user.getIsOAuth()) {
            throw new BaseException(OAUTH_USER);
        }

        switch (user.getUserState()) {
            case PENDING:
                throw new BaseException(PENDING_USER);
            case ACTIVE:
                break;
            case BLOCKED:
                throw new BaseException(BLOCKED_USER);
            case DORMANT:
                throw new BaseException(DORMANT_USER);
            default:
                throw new IllegalStateException("Unexpected user state: " + user.getUserState());
        }

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

    public User getUserByJwt() {
        Long userId = jwtService.getUserId();
        return userRepository.findByIdAndState(userId, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    @Transactional(readOnly = true)
    public List<GetFollowingUserRes> getFollowingUserList(int startPage, int size) {
        User user = getUserByJwt();

        Pageable pageable = PageRequest.of(startPage, size);
        Slice<Follow> followSlice = followRepository.findAllByUserIdAndFollowStateAndState(
                user.getId(), Follow.followState.ACCEPTED, ACTIVE, pageable);
        return followSlice.stream()
                .map(follow -> new GetFollowingUserRes(user, follow.getTargetUser(), follow))
                .collect(Collectors.toList());

    }

    public String updateFollowStatus(Long targetUserId){
        User user = getUserByJwt();
        User targetUser = userRepository.findByIdAndState(targetUserId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (user == targetUser) {
            throw new BaseException(CANNOT_FOLLOW_MY_ACCOUNT);
        }
        Optional<Follow> followOptional = followRepository.findByTargetUserIdAndUserId(targetUserId, user.getId());
        String resultMessage;

        if (followOptional.isPresent()) {
            Follow follow = followOptional.get();
            BaseEntity.State state = follow.getState();
            if (state == ACTIVE){
                if (follow.getFollowState() == Follow.followState.ACCEPTED){
                    follow.deleteFollow();
                    resultMessage = "팔로우 취소 성공";
                } else {
                    throw new BaseException(FOLLOW_REQUEST_EXIST);
                }

            } else {
                // TODO :: 차단된 계정 로직 추가
                follow.createFollowRequest(targetUser);
                resultMessage = "팔로우 요청 성공";
            }
            recordFollowLog(follow, user, targetUser);
        } else {
            Follow tmpFollow = Follow.create();
            tmpFollow.setTargetUser(targetUser);
            tmpFollow.setUser(user);
            tmpFollow.setIsCloseFriend(false);
            tmpFollow.createFollowRequest(targetUser);
            followRepository.save(tmpFollow);
            resultMessage = "팔로우 요청 성공";
            recordFollowLog(tmpFollow, user, targetUser);
        }
        return resultMessage;
    }
    public String updateCloseFriendStatus(Long targetUserId){
        User user = getUserByJwt();
        User targetUser = userRepository.findByIdAndState(targetUserId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (user == targetUser) {
            throw new BaseException(CANNOT_FOLLOW_MY_ACCOUNT);
        }
        Follow follow = followRepository.findByTargetUserIdAndUserIdAndFollowStateAndState(
                targetUserId, user.getId(), Follow.followState.ACCEPTED, ACTIVE).orElseThrow(
                        () -> new BaseException(NOT_FOLLOWING));

        if (follow.getIsCloseFriend()){
            follow.setIsCloseFriend(false);
            recordFollowLog(follow, user, targetUser);
            return "친한 친구 등록 취소 성공";
        } else {
            follow.setIsCloseFriend(true);
            recordFollowLog(follow, user, targetUser);
            return "친한 친구 등록 성공";
        }
    }

    private void recordFollowLog(Follow follow, User user, User targetUser) {
        FollowLog followLog = follow.createFollowLog(user, targetUser);
        followLogRepository.save(followLog);
    }
}
