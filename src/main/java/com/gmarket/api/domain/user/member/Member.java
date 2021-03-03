package com.gmarket.api.domain.user.member;

import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("MEMBER")
@Getter
@PrimaryKeyJoinColumn(name = "member_id")
public class Member extends User {

}
