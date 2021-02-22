package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.BoardStatus;
import com.gmarket.api.domain.board.notice_board.dto.NoticeInfoDto;
import com.gmarket.api.domain.board.notice_board.dto.NoticeRequestDto;
import com.gmarket.api.domain.board.notice_board.dto.NoticeResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NoticeBoardControllerTest {

    @InjectMocks
    NoticeBoardController noticeBoardController;

    @Mock
    NoticeBoardService noticeBoardService;

    private NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
            .status(BoardStatus.CREATE)
            .title("test title")
            .content("test content")
            .userId("test userId")
            .author("test author")
            .build();

    private NoticeResponseDto noticeResponseDto = NoticeResponseDto.builder()
            .id(1L)
            .status(BoardStatus.CREATE)
            .title("test title")
            .content("test content")
            .userId("test userId")
            .author("test author")
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
