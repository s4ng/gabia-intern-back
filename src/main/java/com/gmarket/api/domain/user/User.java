package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static com.gmarket.api.domain.user.enums.UserStatus.*;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public abstract class User extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long userId;

    @Column(unique = true, length = 20) // DB loginId 중복 방지
    private String loginId;

    private String password;

    private String nickname;

    private int activityPoint;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(String loginId, String password, String nickname, int activityPoint, UserStatus status){
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.activityPoint = activityPoint;
        this.status = status;
    }

    public User loginDto(UserDto userDto){
        this.loginId = userDto.getLoginId();
        this.password = userDto.getPassword();
        this.status = CREATED;
        return this;
    }
    public User joinDto(UserDto userDto){
        this.loginId = userDto.getLoginId();
        this.password = userDto.getPassword();
        this.nickname = userDto.getNickname();
        this.status = CREATED;
        return this;
    }

    public User deleteDto(UserDto userDto) {
        this.loginId = userDto.getLoginId();
        this.password = userDto.getPassword();
        this.status = CREATED;
        return this;
    }

    public void delete() {
        this.status = DELETED;
    }

    public User updateDto(UserDto userDto) {
        this.loginId = userDto.getLoginId();
        this.password = userDto.getPassword();
        this.nickname = userDto.getNickname();
        return this;
    }

    public void update(String password, String nickname){
        this.password = password;
        this.nickname = nickname;
    }
}
