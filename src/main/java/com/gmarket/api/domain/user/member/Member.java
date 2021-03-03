package com.gmarket.api.domain.user.member;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@DiscriminatorValue("MEMBER")
@PrimaryKeyJoinColumn(name = "member_id")
@NoArgsConstructor
@JsonTypeName(UserType.Values.MEMBER)
public class Member extends User {

    @Builder
    public Member(String loginId, String password, String nickname, int activityPoint, UserStatus status) {
        super(loginId, password,nickname,activityPoint,status);
    }
}
