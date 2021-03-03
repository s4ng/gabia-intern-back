package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {

    public User findByLoginIdAndStatus(String loginId, UserStatus userStatus);

    public User findByLoginIdAndPassword(String loginId, String password);

}
