package com.gmarket.api.domain.board.notice_board;

import com.gmarket.api.domain.board.notice_board.dto.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeResponseDto create(NoticeRequestDto noticeRequestDto) {
        NoticeBoard noticeBoard = NoticeMapper.INSTANCE.noticeRequestDtoToNoticeBoard(noticeRequestDto);
        return NoticeMapper.INSTANCE.noticeBoardToNoticeResponseDto(noticeBoardRepository.save(noticeBoard));
    }

    public Iterable<NoticeInfoDto> getNoticePage(int page) {

        List<NoticeInfoDto> list = new ArrayList<>();

        noticeBoardRepository.findAll(PageRequest.of(page - 1, 20)).forEach(entity -> {
            list.add(NoticeMapper.INSTANCE.noticeBoardToNoticeInfoDto(entity));
        });

        return list;
    }

    public NoticeInfoDto getNoticeById(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElse(null);

        if(noticeBoard != null && noticeBoard.getDeletedTime() != null){
            return null;
        }

        return NoticeMapper.INSTANCE.noticeBoardToNoticeInfoDto(noticeBoard);
    }

    public NoticeResponseDto updateNotice(NoticeRequestDto noticeRequestDto, Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).orElse(null);
        if(changeBoard == null) {
            return null;
        } else {
            changeBoard.update(noticeRequestDto);
            return NoticeMapper.INSTANCE.noticeBoardToNoticeResponseDto(noticeBoardRepository.save(changeBoard));
        }
    }

    public NoticeResponseDto deleteNotice(Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).orElse(null);
        if(changeBoard == null) {
            return null;
        } else {
            changeBoard.delete();
            return NoticeMapper.INSTANCE.noticeBoardToNoticeResponseDto(noticeBoardRepository.save(changeBoard));
        }
    }
}