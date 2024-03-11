package com.example.demo.src.user;

import com.example.demo.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.example.demo.common.entity.BaseEntity.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndState(Long id, State state);
    Optional<User> findByIdAndUserState(Long id, User.UserState userState);
    Optional<User> findByEmailAndState(String email, State state);

    Optional<User> findByEmailAndUserState(String email, User.UserState userState);
    List<User> findAllByStateNotIn(Collection<State> States);
    List<User> findAllByUserStateNotIn(Collection<User.UserState> userStates);

    List<User> findAllByEmailAndState(String email, State state);
    List<User> findAllByState(State state);

}
