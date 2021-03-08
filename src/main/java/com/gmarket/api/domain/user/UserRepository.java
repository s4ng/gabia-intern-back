package com.gmarket.api.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    // 다형성 구현을 위해서는 JPA 쿼리가 User 엔티티가 아닌, User SubType 엔티티를 조회해야 합니다.
    // 또한, 가급적 쿼리를 사용하지 않고 JPA에서 제공하는 메서드로 Repository를 구현하고자 합니다.
    // Spring data JPA를 활용한 Repository에서 User 엔티티가 아닌 User SubType 엔티티로 조회하는 메서드 구현의 어려움으로 인해
    // 현재로서는 EntityManager 메서드를 이용하여 처리하였습니다.
    // 추후에 공부하여 Spring data jpa Repository의 메서드로 User SubType 엔티티로 조회하도록 변경할 예정입니다.(또는 QueryDsl)
    public User findById(User user, Long userId){
        return em.find(user.getClass(), userId);
    }

}
