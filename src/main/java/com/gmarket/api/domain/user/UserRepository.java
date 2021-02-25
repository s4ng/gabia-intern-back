package com.gmarket.api.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    @PersistenceContext
    private final  EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(String userId) {
        return em.find(User.class, userId);
    }


    public List<User> findByName(String nickname) {
        return em.createQuery("select m from Member m where m.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }
}
