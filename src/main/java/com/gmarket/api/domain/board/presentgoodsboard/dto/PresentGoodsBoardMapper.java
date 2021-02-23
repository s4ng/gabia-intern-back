package com.gmarket.api.domain.board.presentgoodsboard.dto;

import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PresentGoodsBoardMapper {

    PresentGoodsBoardMapper INSTANCE = Mappers.getMapper(PresentGoodsBoardMapper.class);

    PresentGoodsBoard requestDtoToEntity(PresentGoodsBoardRequestDto requestDto);

    default PresentGoodsBoardResponseDto entityToResponseDto(PresentGoodsBoard e) {

        PresentGoodsBoardResponseDto responseDto;
        responseDto = PresentGoodsBoardResponseDto.builder()
                .boardId(e.getBoardId())
                .goodsCategory(e.getGoodsCategory())
                .goodsStatus(e.getGoodsStatus())
                .status(e.getStatus())
                .raffleCloseAt(e.getRaffleCloseAt())
                .title(e.getTitle())
                .description(e.getDescription())
                .userId(e.getUser().getUserId())
                .viewCount(e.getViewCount())
                .build();

        return responseDto;
    }

    PresentGoodsBoardInfoDto entityToInfoDto(PresentGoodsBoard presentGoodsBoard);
}
