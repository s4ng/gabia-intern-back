package com.gmarket.api.domain.user;

import com.gmarket.api.domain.user.dto.ManagerDto;
import com.gmarket.api.domain.user.dto.MemberDto;
import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // springBoot 통합 테스트
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @AutoConfigureTestDatabase 설정값 Replace.NONE - 메모리 DB 아닌, 실 DB로 테스트
@Transactional //  DB에 영향을 줄 때는 항상 transactional 처리
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void 회원가입테스트() throws Exception{
        //given
        UserDto joinUserDto= new MemberDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");

        // when
        UserDto joinUser = userService.joinUser(joinUserDto);

        // then
        assertThat(joinUser.getNickname()).isEqualTo(joinUserDto.getNickname());
    }

    @Test
    public void ID중복가입불가테스트() throws Exception{
        //given
        UserDto joinUserDto1= new MemberDto();
        joinUserDto1.setLoginId("GM1234");
        joinUserDto1.setPassword("gm1234");
        joinUserDto1.setNickname("pablo");

        UserDto joinUserDto2= new MemberDto();
        joinUserDto2.setLoginId("GM1234");
        joinUserDto2.setPassword("test");
        joinUserDto2.setNickname("test");

        // when
        userService.joinUser(joinUserDto1);

        try {
            userService.joinUser(joinUserDto2);
        }catch (IllegalStateException e) {
            return;
        }

        fail("중복처리 불가로 여기까지 실행되지 않고 종료되야함");
    }


    @Test
    public void 로그인성공테스트() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");

        UserDto loginUserDto= new ManagerDto();
        loginUserDto.setLoginId("GM1234");
        loginUserDto.setPassword("gm1234");

        UserDto user = userService.joinUser(joinUserDto);

        System.out.println(user.getNickname());

        // when
        UserDto loginUser = userService.login(loginUserDto);

        // then
        assertThat(user.getLoginId()).isEqualTo(loginUser.getLoginId());
    }

    @Test
    public void 로그인실패테스트ID없음() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");
        userService.joinUser(joinUserDto);

        UserDto loginUserDto= new ManagerDto();
        loginUserDto.setLoginId("x");
        loginUserDto.setPassword("gm1234");
        loginUserDto.setNickname("pablo");

        // when
        try {
            UserDto loginUser = userService.login(loginUserDto);
        }catch (IllegalStateException e) {
            return;
        }

        fail("존재하지 않는 ID 로그인 시도로 여기까지 실행되지 않고 종료되야함");
    }

    @Test
    public void 로그인비밀번호실패테스트() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");
        userService.joinUser(joinUserDto);

        UserDto loginUserDto= new ManagerDto();
        loginUserDto.setLoginId("GM1234");
        loginUserDto.setPassword("x");
        loginUserDto.setNickname("pablo");

        // when
        try {
            UserDto loginUser = userService.login(loginUserDto);
        }catch (IllegalStateException e) {
            return;
        }

        fail("일치하지 않는 비밀번호 로그인 시도로 여기까지 실행되지 않고 종료되야함");
    }

    @Test
    public void 로그인ID조회테스트() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");
        UserDto joinUser = userService.joinUser(joinUserDto);

        String loginId = "GM1234";

        // when
        User loginIdUser = userService.findUserId(loginId);

        // then
        assertThat(joinUser.getUserId()).isEqualTo(loginIdUser.getUserId());
    }

    @Test
    public void 유저정보수정테스트() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");
        UserDto joinUser = userService.joinUser(joinUserDto);

        UserDto updateUserDto= new ManagerDto();
        updateUserDto.setLoginId("GM1234");
        updateUserDto.setPassword("update1");
        updateUserDto.setNickname("update2");

        // when
        UserDto updateUser = userService.updateUser(updateUserDto);

        // then
        assertThat(joinUser.getPassword()).isEqualTo(updateUser.getPassword());
    }

    @Test
    public void 유저삭제테스트() throws Exception{
        //given
        UserDto joinUserDto= new ManagerDto();
        joinUserDto.setLoginId("GM1234");
        joinUserDto.setPassword("gm1234");
        joinUserDto.setNickname("pablo");
        UserDto joinUser = userService.joinUser(joinUserDto);

        UserDto deleteUserDto= new ManagerDto();
        deleteUserDto.setLoginId("GM1234");
        deleteUserDto.setPassword("gm1234");

        // when
        userService.deleteUser(deleteUserDto);
        User user = userService.findUserId(joinUser.getLoginId());
        // then
        assertThat(user).isNull();
    }

}