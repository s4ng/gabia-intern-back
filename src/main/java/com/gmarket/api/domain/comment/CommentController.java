package com.gmarket.api.domain.comment;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.comment.dto.CommentDto;
import com.gmarket.api.domain.comment.enums.BoardType;
import com.gmarket.api.global.util.ResponseWrapperDto;
import com.gmarket.api.global.util.ViewJSON;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Json 형태로 데이터를 반환하기 위해 사용 @Controller + @ResponseBody = @RestController
@RequiredArgsConstructor // @RequiredArgsConstructor 어노테이션은 final, @NonNull 필드 값만 파라미터로 받는 생성자를 만듬
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/boards/{boardType}/comments")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> findBoardPage(@PathVariable("boardType") BoardType boardType, long boardId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(commentService.findPostComments(boardType, boardId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 하나의 게시글 댓글 전체 조회 GetMapping 컨트롤러

    @PostMapping("/boards/{boardType}/comments/")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> createPost(@PathVariable("boardType") BoardType boardType, CommentDto commentDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(commentService.createComment(boardType, commentDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 댓글 생성 PostMapping 컨트롤러

    @PutMapping("/boards/{boardType}/comments/")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> updatePost(@PathVariable("boardType") BoardType boardType, CommentDto commentDto) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(commentService.updateComment(boardType, commentDto))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 댓글 수정 컨트롤러 PutMapping 컨트롤러

    @DeleteMapping("/boards/{boardType}/comments/")
    @JsonView(ViewJSON.Views.class)
    public ResponseEntity<ResponseWrapperDto> deletePost(@PathVariable("boardType") BoardType boardType, long commentId) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(commentService.deleteComment(boardType, commentId))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK);
    } // 댓글 삭제 컨트롤러 DeleteMapping 컨트롤러

}
