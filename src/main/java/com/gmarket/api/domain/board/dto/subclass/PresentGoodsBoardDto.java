package com.gmarket.api.domain.board.dto.subclass;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsCategory;
import com.gmarket.api.domain.board.subclass.presentgoodsboard.enums.PresentGoodsStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PresentGoodsBoardDto extends BoardDto {

    private PresentGoodsCategory presentGoodsCategory;

    private PresentGoodsStatus presentGoodsStatus;

    private LocalDateTime raffleClosedAt;

    private String img;

    public PresentGoodsBoardDto presentGoodsBoardToDto(PresentGoodsBoard presentGoodsBoard){
        entityToDto(presentGoodsBoard);
        this.setUserType(presentGoodsBoard.getUserType());
        this.presentGoodsCategory = presentGoodsBoard.getPresentGoodsCategory();
        this.presentGoodsStatus = presentGoodsBoard.getPresentGoodsStatus();
        this.raffleClosedAt = presentGoodsBoard.getRaffleClosedAt();
        this.img = presentGoodsBoard.getImg();
        return this;
    }

    public PresentGoodsBoardDto(){
        this.setBoardType(BoardType.PRESENT);
    }
}
