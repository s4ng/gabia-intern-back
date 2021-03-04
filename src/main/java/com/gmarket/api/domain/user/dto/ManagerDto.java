package com.gmarket.api.domain.user.dto;

import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.manager.Manager;

public class ManagerDto extends UserDto{

    @Override
    public User loginDtoToEntity(){
        Manager manager = new Manager();
        manager.loginDto( this );
        return manager;
    }

    @Override
    public User joinDtoToEntity() {
        Manager manager = new Manager();
        manager.joinDto( this );
        return manager;
    }

    @Override
    public User updateDtoToEntity() {
        Manager manager = new Manager();
        manager.updateDto( this );
        return manager;
    }

    @Override
    public User deleteDtoToEntity() {
        Manager manager = new Manager();
        manager.deleteDto( this );
        return manager;
    }

    @Override
    public UserDto EntityToResponseDto(User user) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setLoginId(user.getLoginId());
        managerDto.setNickname(user.getNickname());
        managerDto.setActivityPoint(user.getActivityPoint());
        return managerDto;
    }

}
