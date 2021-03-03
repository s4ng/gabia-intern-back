package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 login 서비스
    public User login(User user){
        if(findUserId(user.getLoginId()) == null){
            throw new IllegalStateException("존재하지 않는 ID 입니다.");
        } else if(userRepository.findByLoginIdAndPassword(user.getLoginId(), user.getPassword()) == null ){
            throw new IllegalStateException("일치하지 않는 비밀번호 입니다.");
        }
        User loginUser = userRepository.findByLoginIdAndPassword(user.getLoginId(), user.getPassword());
        return loginUser;
    }

    // loginId 조회 서비스
    public User findUserId(String loginId) {
        return userRepository.findByLoginIdAndStatus(loginId, UserStatus.CREATED); // login ID 조회
    }

}
