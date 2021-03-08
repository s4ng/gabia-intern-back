package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.member.Member;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Before // given
    public void setUp() throws Exception {
        Member member = Member.builder().loginId("gm2201").nickname("pablo").password("pass").activityPoint(0).status(UserStatus.CREATED).build();
        userRepository.save(member);

        entityManager.clear();
    }

    @Test
    public void castingTest() {
        // when
        Member member = (Member) userRepository.findAll().get(0);

        // then
        assertThat(member.getNickname()).isEqualTo("pablo");

    }
}