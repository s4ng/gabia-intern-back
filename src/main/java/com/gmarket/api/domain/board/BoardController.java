package com.gmarket.api.domain.board;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.global.util.ResponseWrapperDto;
import com.gmarket.api.global.util.ViewJSON;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController // Json 형태로 데이터를 반환하기 위해 사용 @Controller + @ResponseBody = @RestController
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/{boardType}/posts")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> findBoardPage(@PathVariable("boardType") BoardType boardType,
                                                            int page) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.findBoardPage(boardType, page))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 게시판 페이지 조회 요청 GetMapping 컨트롤러

    @GetMapping("/boards/{boardType}/posts/{boardId}")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> findPost(@PathVariable("boardType") BoardType boardType,
                                                       @PathVariable("boardId") Long boardId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.findPost(boardType, boardId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 게시글 하나 조회 요청 GetMapping 컨트롤러

    @PostMapping("/boards/{boardType}/posts/")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> createPost(@PathVariable("boardType") BoardType boardType,
                                                         @RequestBody BoardDto boardRequestDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data( boardService.createPost(boardType, boardRequestDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 게시글 하나 생성 PostMapping 컨트롤러

    @PutMapping("/boards/{boardType}/posts/")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> updatePost(@PathVariable("boardType") BoardType boardType,
                                                         @RequestBody BoardDto boardRequestDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.updatePost(boardType, boardRequestDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 게시판 하나 수정 컨트롤러 PutMapping 컨트롤러

    @DeleteMapping("/boards/{boardType}/posts/{boardId}")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> deletePost(@PathVariable("boardType") BoardType boardType,
                                                         @PathVariable("boardId") Long boardId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.deletePost(boardType, boardId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.NO_CONTENT);
    } // 게시판 하나 삭제 컨트롤러 DeleteMapping 컨트롤러
}
