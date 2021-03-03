package com.gmarket.api.domain.user.manager;

import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("MANAGER")
@Getter
@PrimaryKeyJoinColumn(name = "manager_id")
public class Manager extends User {
}
