package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardStatus;
import com.gmarket.api.domain.board.noticeboard.NoticeBoardController;
import com.gmarket.api.domain.board.noticeboard.NoticeBoardService;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeInfoDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeRequestDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeBoardControllerTest {

    @InjectMocks
    NoticeBoardController noticeBoardController;

    @Mock
    NoticeBoardService noticeBoardService;

    private NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
            .status(Board.Status.CREATE)
            .title("test title")
            .description("test content")
            .userId(1L)
            .build();

    private NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
            .boardId(1L)
            .status(Board.Status.CREATE)
            .title("test title")
            .description("test content")
            .userId(1L)
            .build();


    @Test
    void 공지사항_생성() throws Exception {

        when(noticeBoardService.create(noticeRequestDto)).thenReturn(noticeResponseDto);

        noticeBoardController.create(noticeRequestDto);
        verify(noticeBoardService, times(1)).create(noticeRequestDto);
    }

    @Test
    void 공지사항_목록() {
        List<NoticeInfoDto> infoDtoList = new ArrayList<>();
        when(noticeBoardService.getNoticePage(1)).thenReturn(infoDtoList);

        noticeBoardController.list(1);
        verify(noticeBoardService, times(1)).getNoticePage(1);
    }

    @Test
    void 글_조회() {
        NoticeInfoDto noticeInfoDto = NoticeInfoDto.builder().build();
        when(noticeBoardService.getNoticeById(1L)).thenReturn(noticeInfoDto);

        noticeBoardController.findOne(1L);
        verify(noticeBoardService, times(1)).getNoticeById(1L);
    }

    @Test
    void 글_수정() {
        when(noticeBoardService.updateNotice(noticeRequestDto, 1L)).thenReturn(noticeResponseDto);

        noticeBoardController.update(noticeRequestDto, 1L);
        verify(noticeBoardService, times(1)).updateNotice(noticeRequestDto, 1L);
    }

    @Test
    void 글_삭제() {
        when(noticeBoardService.deleteNotice(1L)).thenReturn(noticeResponseDto);

        noticeBoardController.delete(1L);
        verify(noticeBoardService, times(1)).deleteNotice(1L);
    }
}
