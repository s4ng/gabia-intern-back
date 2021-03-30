package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.dto.UserMapper;
import com.gmarket.api.domain.user.dto.UserUpdateDto;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional  // 유저 가입 서비스 로직, 하이웍스 로그인 api 연동시 join 서비스 수정
    public UserDto join(UserDto userDto){
        if (userDto.getGabiaId().isEmpty()){
            throw new IllegalStateException("가비아 ID는 필수로 입력해야합니다");
        }

        if (userRepository.findByGabiaIdAndStatus(userDto.getGabiaId(), UserStatus.CREATED) != null){
            throw new EntityNotFoundException("해당 ID는 이미 가입되어 있습니다");
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

        // 회원 가입은 멤버로만 가입되도록 하며, 추후에 매니저로 변경 될 수 있는 기능 구현 예정
        User user = UserMapper.dtoToEntity(UserType.MEMBER, userDto);

        user.createdStatus();

        // (userType, User SubClass) -> UserDto SubClass
        return UserMapper.entityToDto(UserType.MEMBER, userRepository.save(user));

    }


    // 로그인 시 가비아 아이디 중복 확인을 위한 서비스 로직
    public Map findGabiaId(String gabiaId){

        Map map = new HashMap();

        if(gabiaId.isEmpty()){
            throw new IllegalStateException("확인 할 gabia ID를 입력하세요");
        }

        User user = userRepository.findByGabiaId(gabiaId);

        if( user == null || user.getStatus().equals(UserStatus.DELETED) ) {
            map.put("gabia_id_check", true);
            return map;
        } else {
            map.put("gabia_id_check", false);
            return map;
        }

    }

    // 유저 식별 값으로 유저 정보 조회 서비스 로직
    public UserDto findId(UserType userType, Long userId){
        
        User user =userRepository
                .findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유효하지않은 사용자입니다."));

        if(user.getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴된 유저 입니다.");
        }

        return UserMapper.entityToDto(userType, user);
    }

    public UserDto login( UserDto userDto){

        User user = userRepository.findByGabiaIdAndStatus(userDto.getGabiaId(), UserStatus.CREATED);

        if(user == null){
            throw new IllegalStateException("존재하지 않는 id 입니다");
        }

        if(!user.getPassword().equals(userDto.getPassword())){
            throw new IllegalStateException("비밀 번호가 일치하지 않습니다");
        }

        if(user.getClass().getSimpleName().equals("Member")){
            user.userTypeInput(UserType.MEMBER);
        } else if(user.getClass().getSimpleName().equals("Manager")){
            user.userTypeInput(UserType.MANAGER);
        }

        return UserMapper.entityToDto(user.getUserType(), user);
    }

    @Transactional
    public UserDto update(UserType userType, UserUpdateDto userUpdateDto){
        // Dto check , join 부분과 반복되는 코드가 많아 추후 join 기능이 변경되지 않으면 해당 검증 기능 모듈화 예정
        if (!userType.equals(userUpdateDto.getUserType())){
            throw new IllegalStateException("유저 타입이 일치하지 않습니다");
        }

        User user = userRepository.findByGabiaIdAndStatus(userUpdateDto.getGabiaId(), UserStatus.CREATED);

        if(user == null){
            throw new IllegalStateException("존재하지 않는 id 입니다");
        }

        if(userUpdateDto.getName() != null ){
            if (userUpdateDto.getName().length() > 20){
                throw new IllegalStateException("닉네임은 20자을 넘길 수 없습니다");
            }
        }


        if(userUpdateDto.getOriginPassword() != null){
            if(user.getPassword().equals(userUpdateDto.getOriginPassword())){
                throw new IllegalStateException("기존 비밀번호 정보가 다릅니다");
            }

            if ( userUpdateDto.getNewPassword().length()<4 || userUpdateDto.getNewPassword().length()>12){
                throw new IllegalStateException("비밀번호는 최소 4자 이상 최대 12자까지 입력해야합니다");
            }
        }
        else if(userUpdateDto.getOriginPassword() == null && userUpdateDto.getNewPassword() != null ){
            throw new IllegalStateException("기존 비밀번호 정보를 입력해야 합니다");
        }
        else if(userUpdateDto.getOriginPassword() == null && userUpdateDto.getNewPassword() == null){
            userUpdateDto.setNewPassword(user.getPassword());
        }

        user.update(userUpdateDto); // 정보 수정

        user.userTypeInput(userType); // Dto 응답에 UserType 전달을 위함

        return UserMapper.entityToDto(userType, userRepository.save(user));
    }
//
//    @Transactional
//    public void delete(UserType userType, String gabiaId){
//
//        User user = userRepositoryInterface.findByGabiaIdAndStatus(gabiaId, UserStatus.CREATED);
//
//        if(user == null){
//            throw new IllegalStateException("존재하지 않는 id 입니다");
//        }
//
//        user.deletedStatus(); // 탈퇴 상태로 변경
//
//        userRepositoryInterface.save(user);
//    }

}
