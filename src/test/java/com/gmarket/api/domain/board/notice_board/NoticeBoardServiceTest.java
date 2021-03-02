package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import com.gmarket.api.domain.board.noticeboard.NoticeBoardRepository;
import com.gmarket.api.domain.board.noticeboard.NoticeBoardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoticeBoardServiceTest {

    @InjectMocks
    NoticeBoardService noticeBoardService;

    @Mock
    NoticeBoardRepository noticeBoardRepository;

//    private NoticeRequestDto noticeRequestDto = NoticeRequestDto.builder()
//            .status(BoardStatus.CREATE)
//            .title("test title")
//            .content("test content")
//            .userId("test userId")
//            .author("test author")
//            .build();
//
//    @Test
//    @DisplayName("글_저장")
//    void saveNotice() {
//        when(noticeBoardRepository.save(noticeBoard)).thenReturn(noticeBoard);
//        noticeBoardService.create(noticeRequestDto);
//        verify(noticeBoardRepository, times(1)).save(noticeBoard);
//    }

//    @Test
//    @DisplayName("글_하나_조회")
//    void getNotice() {
//        Optional<NoticeBoard> noticeBoardOptional = Optional.empty();
//        when(noticeBoardRepository.findById(1L)).thenReturn(noticeBoardOptional);
//        noticeBoardService.getNoticeById(1L);
//        verify(noticeBoardRepository).findById(1L);
//    }

    @Test
    @DisplayName("글_리스트_조회")
    void noticeList() {
        Page<NoticeBoard> noticeBoardList = Page.empty();
        when(noticeBoardRepository.findAll(PageRequest.of(0, 20))).thenReturn(noticeBoardList);
        noticeBoardService.getNoticePage(1);
        verify(noticeBoardRepository).findAll(PageRequest.of(0, 20));
    }
}
