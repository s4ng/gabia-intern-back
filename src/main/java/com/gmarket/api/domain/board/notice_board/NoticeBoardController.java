package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.BoardStatus;
import com.gmarket.api.domain.board.notice_board.dto.*;
import com.gmarket.api.global.util.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(value="boards/notice/posts")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    //POST로 공지 추가
    @PostMapping(consumes = "application/json")
    public ResponseDto create(@RequestBody NoticeRequestDto noticeRequestDto) {

        NoticeBoard noticeBoard = NoticeBoard.builder()
                .status(noticeRequestDto.getStatus())
                .title(noticeRequestDto.getTitle())
                .author(noticeRequestDto.getAuthor())
                .content(noticeRequestDto.getContent())
                .userId(noticeRequestDto.getUserId())
                .build();

        NoticeBoard saveResult = noticeBoardService.create(noticeBoard);
        return ResponseDto.builder().result(saveResult).build();
    }

    //테이블 리스트 가져오기
    @GetMapping()
    public ResponseDto list(){
        List<Object> list = new ArrayList<>();
        noticeBoardService.getNoticeList().forEach(ob ->
            list.add(
                    NoticeInfoDto.builder()
                    .id(ob.getId())
                    .title(ob.getTitle())
                    .status(ob.getStatus())
                    .author(ob.getAuthor())
                    .content(ob.getContent())
                    .userId(ob.getUserId())
                    .createdTime(ob.getCreatedTime())
                    .modifiedTime(ob.getModifiedTime())
                    .hit(ob.getHit())
                    .build()
            )
        );
        return ResponseDto.builder().result(list).build();
    }

    //id로 테이블 값 가져오기
    @GetMapping(value = "/{id}")
    public ResponseDto findOne(@PathVariable Long id) {
        return ResponseDto.builder().result(noticeBoardService.getNotice(id)).build();
    }

    //id로 테이블 값 수정
    @PutMapping(value = "/{id}")
    public ResponseDto update(@RequestBody NoticeRequestDto noticeBoardDto, @RequestParam Long id) {
        return ResponseDto.builder().result(noticeBoardService.updateNotice(noticeBoardDto, id)).build();
    }

    //id로 테이블 값 삭제
    @DeleteMapping(value = "/{id}")
    public ResponseDto delete(@RequestParam Long id) {
        return ResponseDto.builder().result(noticeBoardService.deleteNotice(id)).build();
    }
}
