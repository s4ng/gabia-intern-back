package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.noticeboard.NoticeBoard;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeInfoDto;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeMapper;
import com.gmarket.api.domain.board.noticeboard.dto.NoticeResponseDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardInfoDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardMapper;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardRequestDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardResponseDto;
import com.gmarket.api.domain.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PresentGoodsBoardService {

    private final PresentGoodsBoardRepository presentGoodsBoardRepository;
    private final UserRepository userRepository;

    public PresentGoodsBoardService(PresentGoodsBoardRepository presentGoodsBoardRepository,
                                    UserRepository userRepository) {
        this.presentGoodsBoardRepository = presentGoodsBoardRepository;
        this.userRepository = userRepository;
    }

    public PresentGoodsBoardResponseDto create(PresentGoodsBoardRequestDto presentGoodsRequestDto) {
        PresentGoodsBoard presentGoodsBoard =
                PresentGoodsBoardMapper.INSTANCE.presentGoodsRequestDtotoToEntity(presentGoodsRequestDto);
        Long userId = presentGoodsRequestDto.getUserId();
        presentGoodsBoard.setUser(userRepository.getOne(userId));
        return PresentGoodsBoardMapper.INSTANCE.entityToPresentGoodsBoardResponseDto(presentGoodsBoardRepository.save(presentGoodsBoard));
    }

    public Iterable<PresentGoodsBoardInfoDto> getNoticePage(int page) {

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

    public NoticeResponseDto updateNotice(PresentGoodsBoardRequestDto noticeRequestDto, Long id) {

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

    private NoticeInfoDto entityToNoticeInfoDto(NoticeBoard noticeBoard) {
        if(noticeBoard.getUser() == null) {
            return null;
        }
        NoticeInfoDto infoDto = NoticeMapper.INSTANCE.noticeBoardToNoticeInfoDto(noticeBoard);
        infoDto.setUserId(noticeBoard.getUser().getUserId());
        return infoDto;
    }
}
