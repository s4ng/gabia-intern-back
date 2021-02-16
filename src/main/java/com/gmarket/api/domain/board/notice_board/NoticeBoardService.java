package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.notice_board.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    @Autowired
    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeBoard create(NoticeBoard noticeBoard) {
        return (noticeBoardRepository.save(noticeBoard));
    }

    public Optional<NoticeBoard> getNotice(Long id) {
        return noticeBoardRepository.findById(id);
    }

    public Iterable<NoticeBoard> getNoticeList() {
        return noticeBoardRepository.findAll();
    }

    public NoticeBoard updateNotice(NoticeRequestDto noticeBoardDto, Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setTitle(noticeBoardDto.getTitle());
        changeBoard.setContent(noticeBoardDto.getContent());
        changeBoard.setStatus(noticeBoardDto.getStatus());

        return changeBoard;
    }

    public NoticeBoard deleteNotice(Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setDeletedTime(LocalDateTime.now());

        return changeBoard;
    }
}