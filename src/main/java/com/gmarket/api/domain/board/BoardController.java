package com.gmarket.api.domain.board;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.noticeboard.NoticeBoard;
import com.gmarket.api.global.util.JsonViews;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 게시글 저장
    @PostMapping("{boardType}/posts")
    public ResponseEntity<ResponseWrapperDto> save(@PathVariable("boardType") BoardType boardType,
                                                   @RequestBody BoardDto boardDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.save(boardType, boardDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    게시글 저장 PostMapping api 예시 -> domain.com/boards/used/posts

    게시글 저장 RequestBody 예시 ( 중고 게시판 일때 )
{
    "board_type":"USED",
    "user_type":"MANAGER",
    "user_id":"1",
    "status":"CREATED",
    "title":"title1",
    "description":"description1",
    "used_goods_category":"DIGITAL",
    "used_goods_status":"USED",
    "price_suggestion": true
}

    게시글 저장 ResponseBody 예시 ( 중고 게시판 일때 )
{
    "data": {
        "board_type": "USED",
        "user_type": "MANAGER",
        "board_id": 10,
        "user_id": 1,
        "name": "pablo",
        "status": "CREATED",
        "title": "title1",
        "description": "description1",
        "used_goods_category": "DIGITAL",
        "used_goods_status": "USED",
        "price_suggestion": true
    }
}
*/


    @GetMapping("/{boardType}/posts/{boardId}") // 게시글 조회
    public ResponseEntity<ResponseWrapperDto> findId(@PathVariable("boardType") BoardType boardType,
                                                     @PathVariable("boardId") Long boardId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.findId(boardType, boardId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
/*
    게시글 조회 GetMapping api 예시 -> domain.com/boards/notice/posts/1

    게시글 조회 ResponseBody 예시 ( 공지사항 게시판 일때 )
{
    "data": {
        "board_type": "NOTICE",
        "user_type": "MANAGER",
        "board_id": 1,
        "user_id": 1,
        "name": "pablo",
        "status": "CREATED",
        "title": "title1",
        "description": "description1",
        "notice_category": "EVENT"
    }
}
*/

    // 게시글 수정
    @PutMapping ("{boardType}/posts")
    public ResponseEntity<ResponseWrapperDto> update(@PathVariable("boardType") BoardType boardType,
                                                     @RequestBody BoardDto boardDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.update(boardType, boardDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    게시글 수정 PutMapping api 예시 -> domain.com/boards/notice/posts

    게시글 수정 RequestBody 예시 ( 공지사항 게시판 일때 )
{
    "board_id" : "1",
    "board_type":"NOTICE",
    "user_type":"MANAGER",
    "user_id":"1",
    "title":"title1 수정",
    "description":"description 수정",
    "notice_category":"UPDATE"
}

    게시글 수정 ResponseBody 예시 ( 공지사항 게시판 일때 )
{
    "data": {
        "board_type": "NOTICE",
        "user_type": "MANAGER",
        "board_id": 1,
        "user_id": 1,
        "name": "pablo",
        "status": "MODIFIED",
        "title": "title1 수정",
        "description": "description 수정",
        "notice_category": "UPDATE"
    }
}
*/

    // 게시글 삭제
    @DeleteMapping ("{boardType}/posts/{boardId}")
    public ResponseEntity<ResponseWrapperDto> delete(@PathVariable("boardType") BoardType boardType,
                                                     @PathVariable("boardId") Long boardId,
                                                     Long userId) {
        boardService.delete(boardType, boardId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 : [No Content]
    }
    // 게시글 삭제 DeleteMapping api 예시 -> domain.com/boards/notice/posts/1?boardId=3&userId=1

    // 게시판 페이지 조회
    @GetMapping ("{boardType}/posts")
    public ResponseEntity<ResponseWrapperDto> findPage(@PathVariable("boardType") BoardType boardType, int page) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(boardService.findPage(boardType, page))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
/*
    게시판 페이지 조회 GetMapping api 예시 -> domain.com/boards/notice/posts?page=1

    게시글 페이지 조회 ResponseBody 예시 ( 공지사항 게시판 일때 )
{
    "data": [
        {
            "board_type": "NOTICE",
            "user_type": "MANAGER",
            "board_id": 2,
            "user_id": 1,
            "name": "pablo",
            "status": "CREATED",
            "title": "title1",
            "description": "description1",
            "notice_category": "EVENT"
        },
        {
            "board_type": "NOTICE",
            "user_type": "MANAGER",
            "board_id": 1,
            "user_id": 1,
            "name": "pablo",
            "status": "CREATED",
            "title": "title1",
            "description": "description1",
            "notice_category": "EVENT"
        }
    ]
}
*/

}