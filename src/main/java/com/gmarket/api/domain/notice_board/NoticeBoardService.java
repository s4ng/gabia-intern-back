package com.gmarket.api.domain.notice_board;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeBoard register(NoticeBoard noticeBoard) {
        return noticeBoardRepository.save(noticeBoard);
    }

    public Optional<NoticeBoard> getNotice(Long id) {
        return noticeBoardRepository.findById(id);
    }

    public Iterable<NoticeBoard> getNoticeList() {
        return noticeBoardRepository.findAll();
    }

    public void updateNotice(NoticeBoard noticeBoard, Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setContent(noticeBoard.getContent());
        changeBoard.setStatus(noticeBoard.getStatus());
        changeBoard.setTitle(noticeBoard.getTitle());

        noticeBoardRepository.save(changeBoard);
    }

    public void deleteNotice(NoticeBoard noticeBoard, Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).get();
        changeBoard.setDelete_time(noticeBoard.getDelete_time());

        noticeBoardRepository.save(changeBoard);
    }
}