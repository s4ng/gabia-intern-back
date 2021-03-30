package com.gmarket.api.domain.raffle.dto;

import com.gmarket.api.domain.raffle.Raffle;
import com.gmarket.api.domain.raffle.enums.RaffleStatus;
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
    private String gabiaId;
    private RaffleStatus raffleStatus;

    public RaffleDto entityToDto(Raffle raffle) {
        this.id = raffle.getRaffleId();
        this.boardId = raffle.getBoard().getBoardId();
        this.userId = raffle.getUser().getUserId();
        this.gabiaId = raffle.getUser().getGabiaId();
        this.raffleStatus = raffle.getStatus();
        return this;
    }
}
