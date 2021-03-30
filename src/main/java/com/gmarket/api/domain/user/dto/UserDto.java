package com.gmarket.api.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.util.JsonViews;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonView(JsonViews.Response.class) // Dto 클래스를 최소화하여 구현하기 위해 JsonView 활용 
public class UserDto { // Member, Manager 모든 정보가 일치하여 dto subclass 미생성

    private UserType userType;

    private Long userId;

    private String gabiaId;

    private String name;

    @JsonView(JsonViews.Request.class) // 요청
    private String password;

    private int point;

    public UserDto entityToDto(User user){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.gabiaId = user.getGabiaId();
        this.userType = user.getUserType();
        this.point = user.getPoint();
        return this;
    }
}
