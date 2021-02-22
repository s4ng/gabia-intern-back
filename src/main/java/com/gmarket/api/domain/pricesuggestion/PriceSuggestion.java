package com.gmarket.api.domain.pricesuggestion;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PriceSuggestion {
    @Id @GeneratedValue
    private long priceSuggestionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "used_board_Id")
    private Board usedBoardId;

    private int suggestionPrice;
}
