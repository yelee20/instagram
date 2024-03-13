package com.example.demo.src.user;



import com.example.demo.common.entity.BaseEntity.State;
import com.example.demo.common.exceptions.BaseException;
import com.example.demo.src.user.entity.Terms;
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
public class TermsService {

    private final TermsRepository termsRepository;
    private final TermsLogRepository termsLogRepository;


    // Terms log 생성, 조회
    // Terms 조회, 생성, 조회, 삭제
    @Transactional(readOnly = true)
    public List<GetTermsRes> getRequiredTerms() {
        return termsRepository.findAllByIsRequired(true).stream()
                .map(GetTermsRes::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean checkTermsById(Long id) {
        Optional<Terms> result = termsRepository.findByIdAndState(id, ACTIVE);
        return result.isPresent();
    }
}