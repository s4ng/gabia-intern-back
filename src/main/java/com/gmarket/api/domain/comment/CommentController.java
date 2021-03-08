package com.gmarket.api.domain.comment;

import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/boards/{boardType}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping
    public ResponseEntity<ResponseWrapperDto> save(@PathVariable("boardType") BoardType boardType,
                                                   @RequestBody CommentDto commentDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(commentService.save(boardType, commentDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.CREATED); // 201 : [Created]
    }
/*
    댓글 저장 PostMapping api 예시 -> domain.com/boards/used/comments

    댓글 저장 RequestBody 예시 ( 공지 사항 일때 )
{
    "board_type":"NOTICE",
    "board_id" : "1",
    "user_id":"1",
    "comment":"comment1"
}

    게시글 저장 ResponseBody 예시 ( 공지 사항 일때 )
{
    "data": {
        "board_type": "NOTICE",
        "comment_id": 1,
        "board_id": 1,
        "user_id": 1,
        "comment": "comment1",
        "status": "CREATED",
        "created_at": "2021-03-08T15:23:34.1805968",
        "modified_at": "2021-03-08T15:23:34.1805968"
    }
}
*/
}