package com.gmarket.api.domain.user.enums;

import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.subclass.manager.Manager;
import com.gmarket.api.domain.user.subclass.member.Member;
import lombok.Getter;

@Getter
public enum UserType {
    MANAGER, MEMBER;

    // UserType -> User SubClass
    public static User userTypeToSubClass(UserType userType){
        switch (userType){
            case MEMBER:
                return new Member();
            case MANAGER:
                return new Manager();
            default:
                throw new IllegalStateException("정확한 유저 타입을 입력하세요");
        }
    }

    // UserType -> UserDto
    public static UserDto userTypeToSubClassDto(UserType userType){
        switch (userType){
            case MEMBER:
            case MANAGER:
                return new UserDto();
            default:
                throw new IllegalStateException("정확한 유저 타입을 입력하세요");
        }
    }

}
