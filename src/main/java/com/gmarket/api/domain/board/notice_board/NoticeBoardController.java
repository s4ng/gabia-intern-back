package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.notice_board.dto.*;
import com.gmarket.api.global.util.ResponseDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="boards/notice/posts")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseDto create(@RequestBody NoticeRequestDto noticeRequestDto) {
        NoticeResponseDto saveResult = noticeBoardService.create(noticeRequestDto);
        return ResponseDto.builder().result(saveResult).build();
    }

    @GetMapping
    public ResponseDto list(@RequestParam(value = "page", required = false, defaultValue = "1") int page){
        return ResponseDto.builder().result(noticeBoardService.getNoticePage(page)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseDto findOne(@PathVariable Long id) {
        return ResponseDto.builder().result(noticeBoardService.getNoticeById(id)).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseDto update(@RequestBody NoticeRequestDto noticeBoardDto, @PathVariable Long id) {
        return ResponseDto.builder().result(noticeBoardService.updateNotice(noticeBoardDto, id)).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        return ResponseDto.builder().result(noticeBoardService.deleteNotice(id)).build();
    }
}
