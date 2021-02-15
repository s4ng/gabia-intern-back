package com.gmarket.api.domain.board.notice_board;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value="boards/notice/posts")
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    //POST로 공지 추가
    @PostMapping(consumes = "application/json")
    public NoticeBoardDto create(@RequestBody NoticeBoardDto noticeBoardDto) {
        return noticeBoardService.create(noticeBoardDto);
    }

    //테이블 리스트 가져오기
    @GetMapping()
    public Iterable<NoticeBoardDto> list(){
        return noticeBoardService.getNoticeList();
    }

    //id로 테이블 값 가져오기
    @GetMapping(value = "/{id}")
    public Optional<NoticeBoard> findOne(@PathVariable Long id) {
        return noticeBoardService.getNotice(id);
    }

    //id로 테이블 값 수정
    @PutMapping(value = "/{id}")
    public NoticeBoardDto update(@RequestBody NoticeBoardDto noticeBoardDto, @RequestParam Long id) {
        return noticeBoardService.updateNotice(noticeBoardDto, id);
    }

    //id로 테이블 값 삭제
    @DeleteMapping(value = "/{id}")
    public NoticeBoardDto delete(@RequestParam Long id) {
        return noticeBoardService.deleteNotice(id);
    }
}
