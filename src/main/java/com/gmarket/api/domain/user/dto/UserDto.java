package com.gmarket.api.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.domain.user.manager.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeInfo( // controller 에서 SubClass 로 json 주입 받기 위해
        use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "user_type" // "user_type": "member" 일시 Member
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ManagerDto.class, name = UserType.Values.MANAGER),
        @JsonSubTypes.Type(value = MemberDto.class, name = UserType.Values.MEMBER)
})
public abstract class UserDto {

    private Long userId;

    private String loginId;

    private String password;

    private String nickname;

    private int activityPoint;

    public abstract User loginDtoToEntity();
    public abstract User joinDtoToEntity();
    public abstract User updateDtoToEntity();
    public abstract User deleteDtoToEntity();

    public abstract UserDto EntityToResponseDto(User user);

}
