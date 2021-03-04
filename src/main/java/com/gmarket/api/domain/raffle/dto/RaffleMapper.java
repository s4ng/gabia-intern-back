package com.gmarket.api.domain.raffle.dto;

import com.gmarket.api.domain.raffle.Raffle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RaffleMapper {

    RaffleMapper INSTANCE = Mappers.getMapper(RaffleMapper.class);

    @Mappings({
            @Mapping(target = "id", source = "raffleId"),
            @Mapping(target = "presentBoardId", source = "presentBoard.boardId"),
            @Mapping(target = "userId", source = "participant.userId")
    })
    RaffleResponseDto entityToDto(Raffle raffle);
}
