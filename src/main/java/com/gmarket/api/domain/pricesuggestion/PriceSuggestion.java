package com.gmarket.api.domain.pricesuggestion;

import com.gmarket.api.domain.pricesuggestion.dto.PriceSuggestionDto;
import com.gmarket.api.domain.pricesuggestion.enums.PriceSuggestionStatus;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.global.util.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PriceSuggestion extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB increment 따름
    private long priceSuggestionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_Id")
    private Board board;

    private int suggestionPrice;

    private PriceSuggestionStatus status;

    public PriceSuggestion dtoToEntity(PriceSuggestionDto priceSuggestionDto){
        this.priceSuggestionId = priceSuggestionDto.getPriceSuggestionId();
        this.suggestionPrice = priceSuggestionDto.getSuggestionPrice();
        return this;
    }

    public void userAndBoardSetting(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public void update(int suggestionPrice, PriceSuggestionStatus priceSuggestionStatus){
        this.suggestionPrice = suggestionPrice;
        this.status = priceSuggestionStatus;
    }

    public void suggestionStatus(){
        this.status = PriceSuggestionStatus.SUGGESTION;
    }

    public void acceptStatus(){
        this.status = PriceSuggestionStatus.ACCEPT;
    }

}
