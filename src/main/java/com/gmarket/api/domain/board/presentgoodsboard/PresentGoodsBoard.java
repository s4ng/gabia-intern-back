package com.gmarket.api.domain.board.presentgoodsboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.GoodsCategory;
import com.gmarket.api.domain.board.enums.GoodsStatus;
import com.gmarket.api.global.util.ViewJSON;
import lombok.Getter;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PRESENT")
@Getter
@PrimaryKeyJoinColumn(name = "present_board_id")
public class PresentGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Views.class)
    private GoodsCategory goodsCategory;

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Views.class)
    private GoodsStatus goodsStatus;

    @JsonView(ViewJSON.Views.class)
    private LocalDateTime raffleCloseAt;

    public PresentGoodsBoard copySub(PresentGoodsBoard presentGoodsBoard, BoardDto boardDto){
        presentGoodsBoard.goodsCategory = boardDto.getGoodsCategory();
        presentGoodsBoard.goodsStatus = boardDto.getGoodsStatus();

        return presentGoodsBoard;
    }
}
