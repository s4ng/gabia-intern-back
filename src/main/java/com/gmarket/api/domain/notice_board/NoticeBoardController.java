/**
 * 수정해야함 내일
 * 19번 줄 밑으로
 */
package com.gmarket.api.domain.notice_board;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value="/posts")
public class NoticeBoardController {

    private NoticeBoardRepository noticeBoardRepository;

    public NoticeBoardController(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }
    //POST로 공지 추가
    @PostMapping
    public NoticeBoard put(@RequestParam String name, @RequestParam String age) {
        return noticeBoardRepository.save(new NoticeBoard(name, age));
    }

    //테이블 리스트 가져오기
    @GetMapping(value=)
    public Iterable<NoticeBoard> list(){
        return noticeBoardRepository.findAll();
    }

    //id로 테이블 값 가져오기
    @GetMapping(value = "/{id}")
    public Optional<NoticeBoard> findOne(@PathVariable Long id) {
        return noticeBoardRepository.findById(id);
    }

    //id로 테이블 값 수정
    @PutMapping(value = "/{id}")
    public NoticeBoard update(@PathVariable Long id, @RequestParam String name, @RequestParam String age) {
        Optional<NoticeBoard> board = noticeBoardRepository.findById(id);
        board.get().setName(name);
        board.get().setAge(age);
        return boardRep.save(board.get());
    }

    //id로 테이블 값 삭제
    @DeleteMapping
    public void delete(@RequestParam Long id) {
        noticeBoardRepository.deleteById(id);
    }
}
