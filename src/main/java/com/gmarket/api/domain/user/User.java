package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.global.util.ViewJSON;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@JsonView(ViewJSON.Views.class)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public abstract class User {
    @Id @GeneratedValue
    private String userId;

    @JsonView(ViewJSON.Repositorys.class)
    private String password;

    private String nickname;

    private int activityPoint;

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Repositorys.class)
    private UserStatus status;
}
