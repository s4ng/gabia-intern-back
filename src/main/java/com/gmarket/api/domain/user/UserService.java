package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 유저 login 서비스
    public UserDto login(UserDto userDto){
        User user = userDto.loginDtoToEntity();
        if(findUserId(user.getLoginId()) == null){
            throw new IllegalStateException("존재하지 않는 ID 입니다.");
        } else if(userRepository.findByLoginIdAndPassword(user.getLoginId(), user.getPassword()) == null ){
            throw new IllegalStateException("일치하지 않는 비밀번호 입니다.");
        }
        User loginUser = userRepository.findByLoginIdAndPassword(user.getLoginId(), user.getPassword());
        return userDto.EntityToResponseDto(loginUser);
    }

    // loginId 조회 서비스
    public User findUserId(String loginId) {
        return userRepository.findByLoginIdAndStatus(loginId, UserStatus.CREATED); // login ID 조회
    }

    // 유저 회원 가입 서비스
    @Transactional
    public UserDto joinUser(UserDto userDto) {
        User joinUser = userDto.joinDtoToEntity();
        if(findUserId(joinUser.getLoginId()) != null){
            throw new IllegalStateException("이미 존재하는 ID 입니다.");
        }
        userRepository.save(joinUser);
        return userDto.EntityToResponseDto(joinUser);
    }

    // 유저 정보 수정 서비스
    public UserDto updateUser(UserDto userDto){
        User user = userDto.updateDtoToEntity();
        User updateUser = findUserId(user.getLoginId());
        updateUser.update(user.getPassword(), user.getNickname());
        userRepository.save(updateUser);
        return userDto.EntityToResponseDto(updateUser);
    }

    // 유저 삭제 서비스
    public String deleteUser(UserDto userDto){
        User user = userDto.deleteDtoToEntity();
        if(findUserId(user.getLoginId()) == null){
            throw new IllegalStateException("이미 삭제된 ID 입니다.");
        }
        User deleteUser = findUserId(user.getLoginId());
        deleteUser.delete();
        userRepository.save(deleteUser);
        return deleteUser.getLoginId();
    }
}
