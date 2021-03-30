package com.gmarket.api.domain.pricesuggestion;

import com.gmarket.api.domain.alert.Alert;
import com.gmarket.api.domain.alert.AlertRepository;
import com.gmarket.api.domain.alert.dto.AlertDto;
import com.gmarket.api.domain.alert.enums.AlertType;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.BoardRepositoryInterface;
import com.gmarket.api.domain.board.enums.BoardStatus;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import com.gmarket.api.domain.pricesuggestion.dto.PriceSuggestionDto;
import com.gmarket.api.domain.pricesuggestion.enums.PriceSuggestionStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import com.gmarket.api.domain.user.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriceSuggestionService {

    private final PriceSuggestionRepository priceSuggestionRepository;

    private final UserRepository userRepositoryInterface;

    private final BoardRepositoryInterface boardRepositoryInterface;

    private final AlertRepository alertRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public PriceSuggestionDto save(PriceSuggestionDto priceSuggestionDto){

        Optional<User> optionalUser = userRepositoryInterface.findById(priceSuggestionDto.getUserId());

        if(optionalUser.isEmpty()){
            throw new IllegalStateException("유효하지 않은 유저 식별 값 입니다.");
        }

        if(optionalUser.get().getStatus().equals(UserStatus.DELETED)){
            throw new IllegalStateException("이미 탈퇴한 유저 입니다.");
        }

        Board board = boardRepositoryInterface.findById(priceSuggestionDto.getBoardId())
                .orElseThrow( () -> new IllegalStateException("유효하지 않은 게시판 식별 값 입니다.") );

        if(board.getStatus().equals(BoardStatus.CLOSED)
                || board.getStatus().equals(BoardStatus.DELETED) ){
            throw new IllegalStateException("이미 판매 완료 또는 삭제된 게시글은 가격 제안이 불가합니다");
        }

        if(board.getUser().getUserId().equals(priceSuggestionDto.getUserId())){
            throw new IllegalStateException("본인이 작성한 판매 글의 가격 제안은 불가합니다.");
        }

        UsedGoodsBoard usedGoodsBoard = (UsedGoodsBoard ) board;

        if(!usedGoodsBoard.isPriceSuggestion()){
            throw new IllegalStateException("가격 제안 불가 상품 입니다.");
        }

        // 가격 제안 생성
        PriceSuggestion priceSuggestion = new PriceSuggestion().dtoToEntity(priceSuggestionDto);

        priceSuggestion.suggestionStatus();

        priceSuggestion.userAndBoardSetting(optionalUser.get(), usedGoodsBoard);

        priceSuggestionRepository.save(priceSuggestion); // 가격 제안 저장

        // 가격 제안 알림 생성
        String message = "가격제안: "+board.getTitle()+ "\n제시금액: "+ priceSuggestionDto.getSuggestionPrice();

        Alert alert = new Alert().createAlert(board.getUser(), board, message, AlertType.SUGGESTION, BoardType.USED);

        alertRepository.save(alert); // 가격 제안 알림 저장

        // 가격 제안 알림 웹 소켓 전송
        messagingTemplate.convertAndSend(
                "/sub/alert/" + board.getUser().getUserId() , new AlertDto().entityToDto(alert));

        return priceSuggestionDto.entityToDto(priceSuggestion);
    }

    @Transactional
    public PriceSuggestionDto update(PriceSuggestionDto priceSuggestionDto){

        Optional<PriceSuggestion> optionalPriceSuggestion =
                priceSuggestionRepository.findById(priceSuggestionDto.getPriceSuggestionId());

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

        return priceSuggestionDto.entityToDto(priceSuggestionRepository.save(priceSuggestion));
    }

    @Transactional // 가격 수락의 경우 채팅 방 생성 기능이 함께 트랜잭션
                   // 따라서 채팅 관련 Dto가 리턴되어야 하는지 생각
    public PriceSuggestionDto accept(PriceSuggestionDto priceSuggestionDto){

        Optional<PriceSuggestion> optionalPriceSuggestion =
                priceSuggestionRepository.findById(priceSuggestionDto.getPriceSuggestionId());

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

        return priceSuggestionDto.entityToDto(priceSuggestionRepository.save(priceSuggestion));
    }


}
