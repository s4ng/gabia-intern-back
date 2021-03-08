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
}