package com.gmarket.api.domain.raffle.dto;

import com.gmarket.api.domain.raffle.Raffle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RaffleMapper {

    RaffleMapper INSTANCE = Mappers.getMapper(RaffleMapper.class);
    default RaffleResponseDto entityToDto(Raffle raffle) {
        return RaffleResponseDto.builder()
                .id(raffle.getRaffleId())
                .presentBoardId(raffle.getPresentBoard().getBoardId())
                .userId(raffle.getParticipant().getUserId())
                .build();
    }
}
