package com.gmarket.api.domain.notice_board;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public NoticeBoard put(@RequestBody NoticeBoard noticeBoard) {
        return noticeBoardService.register(noticeBoard);
    }

    //테이블 리스트 가져오기
    @GetMapping()
    public Iterable<NoticeBoard> list(){
        return noticeBoardService.getNoticeList();
    }

    //id로 테이블 값 가져오기
    @GetMapping(value = "/{id}")
    public Optional<NoticeBoard> findOne(@PathVariable Long id) {
        return noticeBoardService.getNotice(id);
    }

    //id로 테이블 값 수정
    @PutMapping(value = "/{id}")
    public void update(@RequestBody NoticeBoard noticeBoard, @RequestParam Long id) {
        noticeBoardService.updateNotice(noticeBoard, id);
    }

    //id로 테이블 값 삭제
    @PatchMapping(value = "/{id}")
    public void delete(@RequestBody NoticeBoard noticeBoard, @RequestParam Long id) {
        noticeBoardService.deleteNotice(noticeBoard, id);
    }
}
