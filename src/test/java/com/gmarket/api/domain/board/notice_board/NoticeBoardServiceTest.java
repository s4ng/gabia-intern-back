package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.BoardStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class NoticeBoardServiceTest {

    @Autowired
    private NoticeBoardService noticeBoardService;

    NoticeBoard testPost = NoticeBoard.builder()
            .status(BoardStatus.CREATE)
            .content("test content")
            .title("test title")
            .author("test author")
            .userId("1a")
            .build();

//    @BeforeEach
//    void 초기화() {
//        result = noticeBoardRepository.save(noticeBoard);
//    }

    @Test
    void 글_저장() {
//        NoticeBoard result = noticeBoardService.create(testPost);
//        assert(result.getTitle()).equals("test title");
    }

    @Test
    void 글_하나_조회() {
    }

    @Test
    void 글_리스트_조회() {
    }

    @Test
    void 글_수정() {
    }

    @Test
    void 글_삭제() {
    }
}