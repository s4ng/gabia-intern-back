package com.gmarket.api.domain.raffle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleRequestDto {
    private Long presentBoardId;
    private Long userId;
}
