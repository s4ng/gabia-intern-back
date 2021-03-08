package com.gmarket.api.domain.board.dto.subclass;

import com.gmarket.api.domain.board.dto.BoardDto;
import com.gmarket.api.domain.board.enums.BoardType;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.UsedGoodsBoard;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
import com.gmarket.api.domain.user.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsedGoodsBoardDto extends BoardDto {

    private UsedGoodsCategory usedGoodsCategory;

    private UsedGoodsStatus usedGoodsStatus;

    private Integer sellPrice;

    private boolean priceSuggestion;

    public UsedGoodsBoardDto usedGoodsBoardToDto(UsedGoodsBoard usedGoodsBoard){
        entityToDto(usedGoodsBoard);
        this.setUserType(UserType.MEMBER);
        this.usedGoodsCategory = usedGoodsBoard.getUsedGoodsCategory();
        this.usedGoodsStatus = usedGoodsBoard.getUsedGoodsStatus();
        this.sellPrice = usedGoodsBoard.getSellPrice();
        this.priceSuggestion = usedGoodsBoard.isPriceSuggestion();
        return this;
    }

    public UsedGoodsBoardDto(){
        this.setBoardType(BoardType.USED);
    }
}
