package com.gmarket.api.domain.user.subclass.manager;


import com.gmarket.api.domain.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User{

}
