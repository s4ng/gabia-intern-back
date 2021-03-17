package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByGabiaIdAndStatus(String gabiaId, UserStatus userStatus);

    public User findByGabiaId(String gabiaId);
}
