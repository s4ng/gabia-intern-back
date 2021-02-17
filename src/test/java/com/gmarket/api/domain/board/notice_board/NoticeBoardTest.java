package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.BoardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoticeBoardTest {

    @InjectMocks
    NoticeBoard noticeBoard = NoticeBoard.builder()
            .title("test title")
            .content("test content")
            .status(BoardStatus.CREATE)
            .userId("test userId")
            .author("test author")
            .build();

    @Test
    void entity테스트() {
        assertEquals(noticeBoard.getContent(), "test content");
        assertEquals(noticeBoard.getUserId(), "test userId");
    }

}