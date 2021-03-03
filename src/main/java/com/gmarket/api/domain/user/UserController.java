package com.gmarket.api.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.global.util.ResponseWrapperDto;
import com.gmarket.api.global.util.ViewJSON;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Json 형태로 데이터를 반환하기 위해 사용 @Controller + @ResponseBody = @RestController
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class UserController {

    private final UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<ResponseWrapperDto> login(@RequestBody User user) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.login(user))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 유저 로그인 컨트롤러 (Post)


    @GetMapping("/user") // ex) domain.com/user?loginId=pablo
    public ResponseEntity<ResponseWrapperDto> findUser(String loginId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.findUserId(loginId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 유저 조회 컨트롤러 (Get)

    @PostMapping("/user")
    public ResponseEntity<ResponseWrapperDto> joinUser(@RequestBody User user) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data( userService.joinUser(user))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 유저 회원 가입 컨트롤러 (Post)

    @PutMapping("/user")
    public ResponseEntity<ResponseWrapperDto> updateUser(@RequestBody User user) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.updateUser(user))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 유저 정보 수정 컨트롤러 (Put)

    @DeleteMapping("/user")
    public ResponseEntity<ResponseWrapperDto> deleteUser(@RequestBody User user) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(userService.deleteUser(user))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.NO_CONTENT);
    } // 유저 탈퇴 컨트롤러 (Delete)

}
