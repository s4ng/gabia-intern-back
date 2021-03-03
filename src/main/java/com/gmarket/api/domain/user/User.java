package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.domain.user.manager.Manager;
import com.gmarket.api.domain.user.member.*;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@JsonTypeInfo( // controller 에서 SubClass 로 json 주입 받기 위해
        use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "user_type" // "user_type": "member" 일시 Member
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Manager.class, name = UserType.Values.MANAGER),
        @JsonSubTypes.Type(value = Member.class, name = UserType.Values.MEMBER)
})
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

    public void delete() {
        this.status = UserStatus.DELETED;
    }

    public void update(String password, String nickname) {
        this.password = password;
        this.nickname = nickname;
    }
}
