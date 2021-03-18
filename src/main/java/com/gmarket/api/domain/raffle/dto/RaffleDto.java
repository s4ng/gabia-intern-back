package com.gmarket.api.domain.raffle.dto;

import com.gmarket.api.domain.raffle.Raffle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleDto {
    private Long id;
    private Long boardId;
    private Long userId;

    public RaffleDto entityToDto(Raffle raffle) {
        this.id = raffle.getRaffleId();
        this.boardId = raffle.getBoard().getBoardId();
        this.userId = raffle.getUser().getUserId();
        return this;
    }
}
