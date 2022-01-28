package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.user.dto.UserDto;
//import com.gmarket.api.domain.user.dto.UserUpdateDto;
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
    @PostMapping
    public ResponseEntity<ResponseWrapperDto> join(@RequestBody UserDto userDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.join(userDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    유저 가입 PostMapping api 예시 -> domain.com/users

    유저 가입 RequestBody 예시
{
    "gabia_id":"kcm",
    "password":"1234",
    "name" : "pablo",
}

    유저 가입 ResponseBody 예시
{
    "data": {
        "user_type": "MEMBER",
        "user_id": 1,
        "gabia_id": "kcm",
        "name": "pablo",
        "point": 0
    }
}
*/

    // 유저 가비아 아이디 조회
    @GetMapping
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> findGabiaId(String gabiaId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.findGabiaId(gabiaId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
/*
    유저 조회 GetMapping api 예시 -> domain.com/users?gabiaId=1

    유저 조회 ResponseBody 예시
{
    "data": {
        "user_type": "MEMBER",
        "user_id": 1,
        "gabia_id": "kcm",
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
    @PostMapping("/login")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> login( @RequestBody UserDto userDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.login(userDto))
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
        "created_at": "2021-03-08T20:21:37.4162048",
        "modified_at": "2021-03-08T20:21:37.4162048"
    }
}
*/

    // 정보 수정
//    @PutMapping("/{userType}")
//    @JsonView(JsonViews.Response.class)
//    public ResponseEntity<ResponseWrapperDto>  update(@PathVariable("userType") UserType userType,
//                                                      @RequestBody UserUpdateDto userUpdateDtoDto) {
//        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
//                .data(userService.update(userType, userUpdateDtoDto))
//                .build();
//        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
//    }
/*
    유저 정보 수정 PutMapping api 예시 -> domain.com/users/manager

    유저 정보 수정 RequestBody 예시
{
    "user_type":"MANAGER",
    "user_id" : "1",
    "gabia_id":"gm2201",
    "name" : "pablo_2",
    "originPassword":"1234_2"
    "newPassword" : ""
}

    유저 로그인 ResponseBody 예시
{
    "data": {
        "user_type": "MANAGER",
        "user_id": 1,
        "gabia_id": "gm2201",
        "name": "pablo_2",
        "point": 0
        "created_at": "2021-03-08T20:21:37.4162048",
        "modified_at": "2021-03-08T20:21:37.4162048"
    }
}
*/

    // 탈퇴 요청
//    @DeleteMapping("/{userType}")
//    @JsonView(JsonViews.Response.class)
//    public ResponseEntity<ResponseWrapperDto>  delete(@PathVariable("userType") UserType userType, String gabiaId) {
//        userService.delete(userType, gabiaId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : [No Content]
//    }
    // 탈퇴 요청 DeleteMapping api 예시 -> domain.com/users/manager?gabiaId=gm2201

}
