package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.enums.UserStatus;
import com.gmarket.api.domain.user.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA 관련 테스트 설정만 로드, 기본적으로 @Transactional 어노테이션을 포함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @AutoConfigureTestDatabase 설정값 Replace.NONE - 메모리 DB 아닌, 실 DB로 테스트
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 로그인ID와상태일치테스트() throws Exception {

        // given
        User user = Member.builder().
                loginId("gm2201").
                nickname("pablo").
                password("pass").
                activityPoint(0).
                status(UserStatus.CREATED).
                build();
        userRepository.save(user);

        // when
        User testUser = userRepository.findByLoginIdAndStatus(user.getLoginId(),user.getStatus());

        // then
        assertThat(user).isEqualTo(testUser);
    }

    @Test
    public void 로그인ID와상태다름테스트() throws Exception {

        // given
        User user = Member.builder().
                loginId("gm2201").
                nickname("pablo").
                password("pass").
                activityPoint(0).
                status(UserStatus.CREATED).
                build();
        userRepository.save(user);

        // when
        User testUser1 = userRepository.findByLoginIdAndStatus(user.getLoginId(), UserStatus.DELETED);
        User testUser2 = userRepository.findByLoginIdAndStatus("gm2202",user.getStatus());

        // then
        assertThat(testUser1).isNull();
        assertThat(testUser2).isNull();
    }


    @Test
    public void 로그인ID와패스워드일치테스트() throws Exception {

        // given
        User user = Member.builder().
                loginId("gm2201").
                nickname("pablo").
                password("pass").
                activityPoint(0).
                status(UserStatus.CREATED).
                build();
        userRepository.save(user);

        // when
        User testUser = userRepository.findByLoginIdAndPassword(user.getLoginId(),user.getPassword());

        // then
        assertThat(user).isEqualTo(testUser);
    }

    @Test
    public void 로그인ID와패스워드다름테스트() throws Exception {

        // given
        User user = Member.builder().
                loginId("gm2201").
                nickname("pablo").
                password("pass").
                activityPoint(0).
                status(UserStatus.CREATED).
                build();
        userRepository.save(user);

        // when
        User testUser1 = userRepository.findByLoginIdAndPassword(user.getLoginId(), "pass2");
        User testUser2 =userRepository.findByLoginIdAndPassword("gm2202",user.getLoginId());

        // then
        assertThat(testUser1).isNull();
        assertThat(testUser2).isNull();
    }

}