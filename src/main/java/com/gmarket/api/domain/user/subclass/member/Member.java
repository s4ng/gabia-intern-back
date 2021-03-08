package com.gmarket.api.domain.user.subclass.member;

import com.gmarket.api.domain.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MEMBER")
public class Member extends User{

}
