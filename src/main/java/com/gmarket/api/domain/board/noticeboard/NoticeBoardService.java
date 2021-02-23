package com.gmarket.api.domain.board.noticeboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeInfoDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeMapper;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeRequestDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeResponseDto;
import com.gmarket.api.domain.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;
    private final UserRepository userRepository;

    public NoticeBoardService(NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
        this.userRepository = userRepository;
    }

    public NoticeResponseDto create(NoticeRequestDto noticeRequestDto) {
        NoticeBoard noticeBoard = NoticeMapper.INSTANCE.noticeRequestDtoToNoticeBoard(noticeRequestDto);
        Long userId = noticeRequestDto.getUserId();
        noticeBoard.setUser(userRepository.getOne(userId));
        return NoticeMapper.INSTANCE.noticeBoardToNoticeResponseDto(noticeBoardRepository.save(noticeBoard));
    }

    public Iterable<NoticeInfoDto> getNoticePage(int page) {

        List<NoticeInfoDto> list = new ArrayList<>();

        noticeBoardRepository.findAll(PageRequest.of(page - 1, 20)).forEach(entity -> {
            list.add(entityToNoticeInfoDto(entity));
        });

        return list;
    }

    public NoticeInfoDto getNoticeById(Long id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElse(null);

        if(noticeBoard != null && noticeBoard.getStatus() == Board.Status.DELETE){
            return null;
        }
        noticeBoard.addViewCount();
        noticeBoardRepository.save(noticeBoard);
        return entityToNoticeInfoDto(noticeBoard);
    }

    public NoticeResponseDto updateNotice(NoticeRequestDto noticeRequestDto, Long id) {

        NoticeBoard changeBoard = noticeBoardRepository.findById(id).orElse(null);
        if(changeBoard == null) {
            return null;
        } else {
            changeBoard.update(
                    noticeRequestDto.getStatus(),
                    noticeRequestDto.getTitle(),
                    noticeRequestDto.getDescription()
            );
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

    private NoticeInfoDto entityToNoticeInfoDto(NoticeBoard noticeBoard) {
        if(noticeBoard.getUser() == null) {
            return null;
        }
        NoticeInfoDto infoDto = NoticeMapper.INSTANCE.noticeBoardToNoticeInfoDto(noticeBoard);
        infoDto.setUserId(noticeBoard.getUser().getUserId());
        return infoDto;
    }
}