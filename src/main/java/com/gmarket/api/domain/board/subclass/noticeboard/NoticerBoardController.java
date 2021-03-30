package com.gmarket.api.domain.board.subclass.noticeboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.global.util.JsonViews;
import com.gmarket.api.global.util.ResponseWrapperDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // backend api
@RequiredArgsConstructor // final -> 생성자
@RequestMapping("/boards")
public class NoticerBoardController {

    private final NoticeBoardService noticeBoardService;

    @GetMapping("/{boardType}/posts")
    @JsonView(JsonViews.Response.class)
    public ResponseEntity<ResponseWrapperDto> findPage(@PathVariable("boardType") BoardType boardType, int page ) {
        ResponseWrapperDto responseWrapperDto = ResponseWrapperDto.builder()
                .data(noticeBoardService.findPage(boardType, page))
                .build();
        return new ResponseEntity<>(responseWrapperDto, HttpStatus.OK); // 200 : [OK]
    }
}
