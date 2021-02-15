package com.gmarket.api.domain.board.notice_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    @Autowired
    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeBoardDto create(NoticeBoardDto noticeBoardDto) {
        NoticeBoard noticeBoard = NoticeBoardMapper.INSTANCE.toEntity(noticeBoardDto);
        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.save(noticeBoard));
    }

    public Optional<NoticeBoard> getNotice(Long id) {
        return noticeBoardRepository.findById(id);
    }

    public Iterable<NoticeBoardDto> getNoticeList() {
        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.findAll());
    }

    public NoticeBoardDto updateNotice(NoticeBoardDto noticeBoardDto, Long id) {

        NoticeBoard noticeBoard = NoticeBoardMapper.INSTANCE.toEntity(noticeBoardDto);
        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setContent(noticeBoard.getContent());
        changeBoard.setStatus(noticeBoard.getStatus());
        changeBoard.setTitle(noticeBoard.getTitle());

        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.save(changeBoard));
    }

    public NoticeBoardDto deleteNotice(Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setDeleteTime(LocalDateTime.now());

        return NoticeBoardMapper.INSTANCE.toDto(noticeBoardRepository.save(changeBoard));
    }
}