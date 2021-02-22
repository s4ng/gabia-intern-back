package com.gmarket.api.domain.board.presentgoodsboard.dto;

import com.gmarket.api.domain.board.noticeboard.dto.NoticeResponseDto;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PresentGoodsBoardMapper {

    PresentGoodsBoardMapper INSTANCE = Mappers.getMapper(PresentGoodsBoardMapper.class);

    PresentGoodsBoard presentGoodsRequestDtotoToEntity(PresentGoodsBoardRequestDto presentGoodsBoardRequestDto);

    PresentGoodsBoardRequestDto entityToPresentGoodsBoardRequestDto(PresentGoodsBoard presentGoodsBoard);

    default PresentGoodsBoardResponseDto entityToPresentGoodsBoardResponseDto(PresentGoodsBoard e) {
        PresentGoodsBoardResponseDto responseDto;
        responseDto = PresentGoodsBoardResponseDto.builder()
                .boardId(e.getBoardId())
                .goodsCategory(e.getGoodsCategory())
                .goodsStatus(e.getGoodsStatus())
                .raffleCloseAt(e.getRaffleCloseAt())
                .title(e.getTitle())
                .description(e.getDescription())
                .userId(e.getUser().getUserId())
                .viewCount(e.getViewCount())
                .build();

        return responseDto;
    }
}
