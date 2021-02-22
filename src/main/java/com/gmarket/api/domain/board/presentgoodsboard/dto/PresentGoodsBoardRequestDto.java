package com.gmarket.api.domain.board.presentgoodsboard.dto;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresentGoodsBoardRequestDto {
    private PresentGoodsBoard.GoodsCategory goodsCategory;
    private PresentGoodsBoard.GoodsStatus goodsStatus;
    private LocalDateTime raffleCloseAt;
    private Board.Status status;
    private String title;
    private String description;
    private Long userId;
    private int viewCount;
}
