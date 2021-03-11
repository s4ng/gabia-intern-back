package com.gmarket.api.domain.pricesuggestion;


import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepository;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import com.gmarket.api.domain.pricesuggestion.enums.PriceSuggestionStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.pricesuggestion.dto.PriceSuggestionDto;
import com.gmarket.api.domain.user.UserRepositoryInterface;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceSuggestionService {

    private final PriceSuggestionRepositoryInterface priceSuggestionRepositoryInterface;

    private final UserRepositoryInterface userRepositoryInterface;

    private final BoardRepository boardRepository;

    @Transactional
    public PriceSuggestionDto save(PriceSuggestionDto priceSuggestionDto) {

        Optional<User> optionalUser = userRepositoryInterface.findById(priceSuggestionDto.getUserId());

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("유효하지 않은 유저 식별 값 입니다.");
        }

        if (optionalUser.get().getStatus().equals(UserStatus.DELETED)) {
            throw new IllegalStateException("이미 탈퇴한 유저 입니다.");
        }

        Board board = boardRepository.findById(new UsedGoodsBoard(), priceSuggestionDto.getBoardId());

        if (board == null) {
            throw new IllegalStateException("유효하지 않은 게시판 식별 값 입니다.");
        }

        UsedGoodsBoard usedGoodsBoard = (UsedGoodsBoard) board;

        if (usedGoodsBoard.getUser().getUserId().equals(priceSuggestionDto.getUserId())) {
            throw new IllegalStateException("판매 글의 작성자는 본인이 작성한 판매 글의 가격 제안이 불가합니다.");
        }

        if (!usedGoodsBoard.isPriceSuggestion()) {
            throw new IllegalStateException("가격 제안 불가 상품 입니다.");
        }

        PriceSuggestion priceSuggestion = new PriceSuggestion();

        priceSuggestion.dtoToEntity(priceSuggestionDto);

        priceSuggestion.suggestionStatus();

        priceSuggestion.userAndBoardSetting(optionalUser.get(), board);

        return priceSuggestionDto.entityToDto(priceSuggestionRepositoryInterface.save(priceSuggestion));
    }

    @Transactional
    public PriceSuggestionDto update(PriceSuggestionDto priceSuggestionDto){

        Optional<PriceSuggestion> optionalPriceSuggestion =
                priceSuggestionRepositoryInterface.findById(priceSuggestionDto.getPriceSuggestionId());

        if(optionalPriceSuggestion.isEmpty()){
            throw new IllegalStateException("가격 제안 조회 내역이 존재하지 않습니다.");
        }

        if(!optionalPriceSuggestion.get().getStatus().equals(PriceSuggestionStatus.SUGGESTION)){
            throw new IllegalStateException("가격 제안 상태가 아닙니다.");
        }

        if(!optionalPriceSuggestion.get().getUser().getUserId().equals(priceSuggestionDto.getUserId())){
            throw new IllegalStateException("가격 제안 유저 식별 정보가 일치하지 않습니다");
        }

        if(!optionalPriceSuggestion.get().getBoard().getBoardId().equals(priceSuggestionDto.getBoardId())){
            throw new IllegalStateException("가격 제안 게시글 식별 정보가 일치하지 않습니다");
        }

        PriceSuggestion priceSuggestion = optionalPriceSuggestion.get();

        priceSuggestion.update(priceSuggestionDto.getSuggestionPrice(), priceSuggestionDto.getStatus());

        return priceSuggestionDto.entityToDto(priceSuggestionRepositoryInterface.save(priceSuggestion));
    }

    @Transactional // 가격 수락의 경우 채팅 방 생성 기능이 함께 트랜잭션
    // 따라서 채팅 관련 Dto가 리턴되어야 하는지 생각
    public PriceSuggestionDto accept(PriceSuggestionDto priceSuggestionDto){

        Optional<PriceSuggestion> optionalPriceSuggestion =
                priceSuggestionRepositoryInterface.findById(priceSuggestionDto.getPriceSuggestionId());

        if(optionalPriceSuggestion.isEmpty()){
            throw new IllegalStateException("가격 제안 조회 내역이 존재하지 않습니다.");
        }

        if(!optionalPriceSuggestion.get().getStatus().equals(PriceSuggestionStatus.SUGGESTION)){
            throw new IllegalStateException("가격 제안 상태가 아닙니다.");
        }

        if(!optionalPriceSuggestion.get().getUser().getUserId().equals(priceSuggestionDto.getUserId())){
            throw new IllegalStateException("가격 제안 유저 식별 정보가 일치하지 않습니다");
        }

        if(!optionalPriceSuggestion.get().getBoard().getBoardId().equals(priceSuggestionDto.getBoardId())){
            throw new IllegalStateException("가격 제안 게시글 식별 정보가 일치하지 않습니다");
        }

        PriceSuggestion priceSuggestion = optionalPriceSuggestion.get();

        priceSuggestion.acceptStatus();

        return priceSuggestionDto.entityToDto(priceSuggestionRepositoryInterface.save(priceSuggestion));
    }

}