package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.user.dto.UserDto;
import com.gmarket.api.domain.user.enums.UserType;
import com.gmarket.api.global.util.JsonViews;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 유저 가입
    @PostMapping("/{userType}")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> join(@PathVariable("userType") UserType userType, @RequestBody UserDto userDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.join(userType, userDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    유저 가입 PostMapping api 예시 -> domain.com/users/manager

    유저 가입 RequestBody 예시
{
    "user_type":"MANAGER",
    "gabia_id":"gm2201",
    "name" : "pablo",
    "password":"1234"
}

    유저 가입 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo",
        "point": 0
    }
}
*/

    // 유저 조회
    @GetMapping("/{userType}")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> findId(@PathVariable("userType") UserType userType, Long userId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.findId(userType, userId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
/*
    유저 조회 GetMapping api 예시 -> domain.com/users/manager?userId=1

    유저 조회 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo",
        "point": 0
    }
}
*/

    // 로그인
    @PostMapping("/{userType}/login")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> login(@PathVariable("userType") UserType userType, @RequestBody UserDto userDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.login(userType, userDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
/*
    유저 로그인 PostMapping api 예시 -> domain.com/users/manager/login

    유저 로그인 RequestBody 예시
{
    "user_type":"MANAGER",
    "gabia_id":"gm2201",
    "password":"1234"
}

    유저 로그인 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo",
        "point": 0
    }
}
*/

    // 정보 수정
    @PutMapping("/{userType}")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto>  update(@PathVariable("userType") UserType userType, @RequestBody UserDto userDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.update(userType, userDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    유저 정보 수정 PutMapping api 예시 -> domain.com/users/manager

    유저 정보 수정 RequestBody 예시
{
    "user_type":"MANAGER",
    "user_id" : "1",
    "gabia_id":"gm2201",
    "name" : "pablo_2",
    "password":"1234_2"
}

    유저 로그인 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo_2",
        "point": 0
    }
}
*/

    // 탈퇴 요청
    @DeleteMapping("/{userType}")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto>  delete(@PathVariable("userType") UserType userType,
                                                      String gabiaId, String password) {
        userService.delete(userType, gabiaId, password);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : [No Content]
    }

/*
    탈퇴 요청 DeleteMapping api 예시 -> domain.com/users/manager?

    유저 로그인 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo_2",
        "point": 0
    }
}
*/
}
