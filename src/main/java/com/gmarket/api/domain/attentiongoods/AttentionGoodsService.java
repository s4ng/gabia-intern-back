package com.gmarket.api.domain.attentiongoods;

import com.gmarket.api.domain.attentiongoods.dto.AttentionGoodsDto;
import com.gmarket.api.domain.attentiongoods.enums.AttentionGoodsStatus;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttentionGoodsService {

    private final AttentionGoodsRepositoryInterface attentionGoodsRepositoryInterface;

    private final BoardRepositoryInterface boardRepositoryInterface;

    private final UserRepository userRepositoryInterface;

    public AttentionGoodsDto save(AttentionGoodsDto attentionGoodsDto){

        if(attentionGoodsDto.getUserId() == null){
            throw new IllegalStateException("유저 식별 정보가 존재하지 않습니다");
        }

        Optional<User> optionalUser = userRepositoryInterface.findById(attentionGoodsDto.getUserId());

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("유저 식별 정보가 유효하지 않습니다");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴한 유저입니다");
        }

        if(attentionGoodsDto.getBoardId() == null){
            throw new IllegalStateException("관심 상품 식별 정보가 존재하지 않습니다");
        }

        Optional<Board> optionalBoard = boardRepositoryInterface.findById(attentionGoodsDto.getBoardId());

        if(optionalBoard.isEmpty()){
            throw new IllegalStateException("관심 상품 식별 정보가  유효하지 않습니다");
        }

        if(optionalBoard.get().getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("이미 삭제된 상품입니다");
        }


        AttentionGoods attentionGoods = attentionGoodsRepositoryInterface.findByUserAndBoard(optionalUser.get(), optionalBoard.get());

        if(attentionGoods == null){
            AttentionGoods attentionGoods1 = new AttentionGoods();
            attentionGoods1.dtoToEntity(attentionGoodsDto);
            attentionGoods1.userAndBoardSetting(optionalUser.get(), optionalBoard.get());
            return attentionGoodsDto.entityToDto(attentionGoodsRepositoryInterface.save(attentionGoods1));
        } else {
            if(attentionGoods.getStatus().equals(AttentionGoodsStatus.REGISTERED)){
                throw new IllegalStateException("이미 관심 상품으로 등록되어 있습니다");
            }

            attentionGoods.registeredStatus();
            return attentionGoodsDto.entityToDto(attentionGoodsRepositoryInterface.save(attentionGoods));
        }
    }


    @Transactional
    public void delete(Long attentionGoodsId){

        Optional<AttentionGoods> optionalAttentionGoods = attentionGoodsRepositoryInterface.findById(attentionGoodsId);

        if (optionalAttentionGoods.isEmpty()){
            throw new IllegalStateException("관심 상품 내역이 유효하지 않습니다");
        }

        if(optionalAttentionGoods.get().getStatus().equals(AttentionGoodsStatus.CANCEL)){
            throw new IllegalStateException("이미 관심 상품 등록 취소 처리 되어 있습니다");
        }

        Optional<Board> optionalBoard = boardRepositoryInterface.findById(
                optionalAttentionGoods.get().getBoard().getBoardId());

        if(optionalBoard.get().getStatus().equals(BoardStatus.DELETED)){
            throw new IllegalStateException("이미 삭제 된 상품 입니다");
        }

        AttentionGoods attentionGoods = optionalAttentionGoods.get();

        attentionGoods.cancelStatus();

        attentionGoodsRepositoryInterface.save(attentionGoods);
    }


    public List<AttentionGoodsDto> list(Long userId){

        Optional<User> optionalUser = userRepositoryInterface.findById(userId);

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("유저 식별 정보가 유효하지 않습니다");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴한 유저입니다");
        }

        List<AttentionGoods> attentionGoodsList = attentionGoodsRepositoryInterface.
                findByStatusAndUser(AttentionGoodsStatus.REGISTERED, optionalUser.get());


        List<AttentionGoodsDto> attentionGoodsDtoList = new ArrayList<>();

        for(AttentionGoods attentionGoods: attentionGoodsList){
            AttentionGoodsDto attentionGoodsDto = new AttentionGoodsDto();
            attentionGoodsDtoList.add(attentionGoodsDto.entityToDto(attentionGoods));
        }

        return attentionGoodsDtoList;
    }
}
