package com.gmarket.api.domain.user.manager;

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
@DiscriminatorValue("MANAGER")
@PrimaryKeyJoinColumn(name = "manager_id")
@NoArgsConstructor
@JsonTypeName(UserType.Values.MANAGER)
public class Manager extends User {

    @Builder
    public Manager(String loginId, String password, String nickname, int activityPoint, UserStatus status) {
        super(loginId, password,nickname,activityPoint,status);
    }
}
