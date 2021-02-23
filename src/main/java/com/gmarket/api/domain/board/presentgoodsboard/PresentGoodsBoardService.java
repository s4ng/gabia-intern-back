package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardInfoDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardMapper;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardRequestDto;
import com.gmarket.api.domain.board.presentgoodsboard.dto.PresentGoodsBoardResponseDto;
import com.gmarket.api.domain.user.User;
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
        Long userId = presentGoodsRequestDto.getUserId();
        User user = userRepository.getOne(userId);
        PresentGoodsBoard presentGoodsBoard =
                PresentGoodsBoardMapper.INSTANCE.requestDtoToEntity(presentGoodsRequestDto);
        presentGoodsBoard.setRaffleCloseAt(presentGoodsRequestDto);
        presentGoodsBoard.setUser(user);
        return PresentGoodsBoardMapper.INSTANCE.entityToResponseDto(presentGoodsBoardRepository.save(presentGoodsBoard));
    }

    public Iterable<PresentGoodsBoardInfoDto> getPage(int page) {

        List<PresentGoodsBoardInfoDto> list = new ArrayList<>();

        presentGoodsBoardRepository.findAll(PageRequest.of(page - 1, 20)).forEach(entity ->
            list.add(entityToInfoDto(entity))
        );

        return list;
    }

    public PresentGoodsBoardInfoDto getById(Long id) {
        PresentGoodsBoard board = presentGoodsBoardRepository.findById(id).orElse(null);

        if(board == null || board.getStatus() == Board.Status.DELETE){
            return null;
        }
        board.addViewCount();
        presentGoodsBoardRepository.save(board);
        return entityToInfoDto(board);
    }

    public PresentGoodsBoardResponseDto update(PresentGoodsBoardRequestDto requestDto, Long id) {

        PresentGoodsBoard changeBoard = presentGoodsBoardRepository.findById(id).orElse(null);
        if(changeBoard == null) {
            return null;
        } else {
            changeBoard.update(
                    requestDto.getStatus(),
                    requestDto.getTitle(),
                    requestDto.getDescription()
            );
            return PresentGoodsBoardMapper.INSTANCE.entityToResponseDto(presentGoodsBoardRepository.save(changeBoard));
        }
    }

    public PresentGoodsBoardResponseDto delete(Long id) {

        PresentGoodsBoard changeBoard = presentGoodsBoardRepository.findById(id).orElse(null);
        if(changeBoard == null) {
            return null;
        } else {
            changeBoard.delete();
            return PresentGoodsBoardMapper.INSTANCE.entityToResponseDto(presentGoodsBoardRepository.save(changeBoard));
        }
    }

    private PresentGoodsBoardInfoDto entityToInfoDto(PresentGoodsBoard presentGoodsBoard) {
        if(presentGoodsBoard.getUser() == null) {
            return null;
        }
        PresentGoodsBoardInfoDto infoDto = PresentGoodsBoardMapper.INSTANCE.entityToInfoDto(presentGoodsBoard);
        infoDto.setUserId(presentGoodsBoard.getUser().getUserId());
        return infoDto;
    }
}
