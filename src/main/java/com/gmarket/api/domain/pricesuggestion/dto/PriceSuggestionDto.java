package com.gmarket.api.domain.pricesuggestion.dto;

import com.gmarket.api.domain.pricesuggestion.PriceSuggestion;
import com.gmarket.api.domain.pricesuggestion.enums.PriceSuggestionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PriceSuggestionDto {

    private long priceSuggestionId;

    private Long userId;

    private Long boardId;

    private int suggestionPrice;

    private PriceSuggestionStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public PriceSuggestionDto entityToDto(PriceSuggestion priceSuggestion){
        this.priceSuggestionId = priceSuggestion.getPriceSuggestionId();
        this.userId = priceSuggestion.getUser().getUserId();
        this.boardId = priceSuggestion.getBoard().getBoardId();
        this.suggestionPrice = priceSuggestion.getSuggestionPrice();
        this.status = priceSuggestion.getStatus();
        this.createdAt = priceSuggestion.getCreatedAt();
        this.modifiedAt = priceSuggestion.getModifiedAt();
        return this;
    }
}
