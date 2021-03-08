package com.gmarket.api.domain.user.dto;

import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserType;

public class UserMapper {

    static public User dtoToEntity(UserType userType, UserDto userDto){
        User user = UserType.userTypeToSubClass(userType); // UserType -> User SubClass
        user.dtoToEntity(userDto);
        return  user;
    }

    static public UserDto entityToDto(UserType userType, User user){
        UserDto userDto = UserType.userTypeToSubClassDto(userType);
        userDto.entityToDto(user);
        return userDto;
    }

}
