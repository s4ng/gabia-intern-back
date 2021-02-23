package com.gmarket.api.domain.board.presentgoodsboard.dto;

import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PresentGoodsBoardMapper {

    PresentGoodsBoardMapper INSTANCE = Mappers.getMapper(PresentGoodsBoardMapper.class);

    PresentGoodsBoard requestDtoToEntity(PresentGoodsBoardRequestDto requestDto);

    default PresentGoodsBoardResponseDto entityToResponseDto(PresentGoodsBoard entity) {

        PresentGoodsBoardResponseDto responseDto;
        responseDto = PresentGoodsBoardResponseDto.builder()
                .boardId(entity.getBoardId())
                .goodsCategory(entity.getGoodsCategory())
                .goodsStatus(entity.getGoodsStatus())
                .status(entity.getStatus())
                .raffleCloseAt(entity.getRaffleCloseAt())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .userId(entity.getUser().getUserId())
                .viewCount(entity.getViewCount())
                .build();

        return responseDto;
    }

    PresentGoodsBoardInfoDto entityToInfoDto(PresentGoodsBoard presentGoodsBoard);
}
