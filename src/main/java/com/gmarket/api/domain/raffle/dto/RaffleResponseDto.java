package com.gmarket.api.domain.raffle.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaffleResponseDto {
    private Long id;
    private Long presentBoardId;
    private Long userId;
}
