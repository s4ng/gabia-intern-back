package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.dto.UserMapper;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositoryInterface userRepositoryInterface;

    @Transactional  // 하이웍스 로그인 api 연동시 join 서비스 수정
    public UserDto join(UserType userType, UserDto userDto){

        // Dto check
        if (!userType.equals(userDto.getUserType())){
            throw new IllegalStateException("유저 타입이 일치하지 않습니다");
        }

        if (userDto.getGabiaId().isEmpty()){
            throw new IllegalStateException("가비아 ID는 필수로 입력해야합니다");
        }

        if (userRepositoryInterface.findByGabiaIdAndStatus(userDto.getGabiaId(), UserStatus.CREATED) != null){
            throw new IllegalStateException("해당 ID는 이미 가입되어 있습니다");
        }

        if (userDto.getName().isEmpty()){
            throw new IllegalStateException("닉네임은 필수로 입력해야합니다");
        }

        if (userDto.getName().length() > 20){
            throw new IllegalStateException("닉네임은 20자을 넘길 수 없습니다");
        }

        if (userDto.getPassword().isEmpty()){
            throw new IllegalStateException("비밀번호는 필수로 입력해야합니다");
        }

        if ( userDto.getPassword().length()<4 || userDto.getPassword().length()>12){
            throw new IllegalStateException("비밀번호는 최소 4자 이상 최대 12자까지 입력해야합니다");
        }

        // (userType, UserDto SubClass) -> User SubClass
        User user = UserMapper.dtoToEntity(userType, userDto);

        user.createdStatus();

        // (userType, User SubClass) -> UserDto SubClass
        return UserMapper.entityToDto(userType, userRepositoryInterface.save(user));
    }

    public UserDto findId(UserType userType, Long userId){
        // User 엔티티가 아닌 User SubClass 엔티티 조회를 위해 EntityManager 활용한 userRepository 구현
        User user = userRepository.findById(UserType.userTypeToSubClass(userType), userId);

        if(user == null){
            throw new IllegalStateException("유저 타입 불일치 또는 존재하지 않는 id 입니다");
        }

        user.userTypeInput(userType); // Dto 응답에 UserType 전달을 위함

        return UserMapper.entityToDto(userType, user);
    }

    public UserDto login(UserType userType, UserDto userDto){

        if (!userType.equals(userDto.getUserType())){
            throw new IllegalStateException("유저 타입이 일치하지 않습니다");
        }

        User user = userRepositoryInterface.findByGabiaIdAndStatus(userDto.getGabiaId(), UserStatus.CREATED);

        if(user == null){
            throw new IllegalStateException("존재하지 않는 id 입니다");
        }

        if(!user.getPassword().equals(userDto.getPassword())){
            throw new IllegalStateException("비밀 번호가 일치하지 않습니다");
        }

        user.userTypeInput(userType); // Dto 응답에 UserType 전달을 위함

        return UserMapper.entityToDto(userType, user);
    }

    @Transactional
    public UserDto update(UserType userType, UserDto userDto){
        // Dto check , join 부분과 반복되는 코드가 많아 추후 join 기능이 변경되지 않으면 해당 검증 기능 모듈화 예정
        if (!userType.equals(userDto.getUserType())){
            throw new IllegalStateException("유저 타입이 일치하지 않습니다");
        }

        User user = userRepositoryInterface.findByGabiaIdAndStatus(userDto.getGabiaId(), UserStatus.CREATED);

        if(user == null){
            throw new IllegalStateException("존재하지 않는 id 입니다");
        }

//        if (userDto.getGabiaId().isEmpty()){  // front end에서 gabiaID는 변경 불가
//            throw new IllegalStateException("가비아 ID는 필수로 입력해야합니다");
//        }

        if (userDto.getName().isEmpty()){
            throw new IllegalStateException("닉네임은 필수로 입력해야합니다");
        }
        if (userDto.getName().length() > 20){
            throw new IllegalStateException("닉네임은 20자을 넘길 수 없습니다");
        }
        if (userDto.getPassword().isEmpty()){
            throw new IllegalStateException("비밀번호는 필수로 입력해야합니다");
        }
        if ( userDto.getPassword().length()<4 || userDto.getPassword().length()>12){
            throw new IllegalStateException("비밀번호는 최소 4자 이상 최대 12자까지 입력해야합니다");
        }

        user.update(userDto); // 정보 수정

        user.userTypeInput(userType); // Dto 응답에 UserType 전달을 위함

        return UserMapper.entityToDto(userType, userRepositoryInterface.save(user));
    }

    @Transactional
    public void delete(UserType userType, String gabiaId, String password){

        User user = userRepositoryInterface.findByGabiaIdAndStatus(gabiaId, UserStatus.CREATED);

        if(user == null){
            throw new IllegalStateException("존재하지 않는 id 입니다");
        }

        if(!password.equals(user.getPassword())){
            throw new IllegalStateException("일치하지 않는 비밀번호 입니다");
        }

        user.deletedStatus(); // 탈퇴 상태로 변경

        userRepositoryInterface.save(user);
    }

}
