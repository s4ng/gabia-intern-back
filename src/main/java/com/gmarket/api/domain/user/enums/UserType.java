package com.gmarket.api.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserType {
    MANAGER, MEMBER;

    private String type;

    public static class Values {
        public static final String MANAGER = "manager";
        public static final String MEMBER = "member";
    }

}
