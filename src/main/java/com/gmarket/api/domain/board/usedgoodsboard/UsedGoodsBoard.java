package com.gmarket.api.domain.board.usedgoodsboard;

import com.fasterxml.jackson.annotation.JsonView;
import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.GoodsCategory;
import com.gmarket.api.domain.board.enums.GoodsStatus;
import com.gmarket.api.global.util.ViewJSON;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("USED")
@Getter @Setter
@PrimaryKeyJoinColumn(name = "used_board_id")
public class UsedGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Views.class)
    private GoodsCategory goodsCategory;

    @Enumerated(EnumType.STRING)
    @JsonView(ViewJSON.Views.class)
    private GoodsStatus goodsStatus;

    @JsonView(ViewJSON.Views.class)
    private int sellPrice;

    public UsedGoodsBoard copySub(UsedGoodsBoard usedGoodsBoard, BoardDto boardDto){
        usedGoodsBoard.goodsCategory = boardDto.getGoodsCategory();
        usedGoodsBoard.goodsStatus = boardDto.getGoodsStatus();
        usedGoodsBoard.sellPrice = boardDto.getSellPrice();
        return usedGoodsBoard;
    }
}
