package com.gmarket.api.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // id 유저 정보 조회 메소드
    public User findOne(String userID) {
        return userRepository.findOne(userID); // 식별키로 조회
    }

    // 유저 생성 메소드
    @Transactional //변경
    public String join(User user) {
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user); // 회원 저장
        return user.getUserId();
    } // 비즈니스와 커맨드 분리



    // 회원 유효성 검사
    private void validateDuplicateMember(User user) {
        List<User> findUser =
                userRepository.findByName(user.getNickname());
        if (!findUser.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }



}
