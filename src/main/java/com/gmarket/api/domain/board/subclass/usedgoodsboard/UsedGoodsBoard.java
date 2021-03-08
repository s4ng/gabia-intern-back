package com.gmarket.api.domain.board.subclass.usedgoodsboard;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.board.dto.subclass.UsedGoodsBoardDto;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsCategory;
import com.gmarket.api.domain.board.subclass.usedgoodsboard.enums.UsedGoodsStatus;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@DiscriminatorValue("USED")
public class UsedGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    private UsedGoodsCategory usedGoodsCategory;

    @Enumerated(EnumType.STRING)
    private UsedGoodsStatus usedGoodsStatus;

    private int sellPrice;

    private boolean priceSuggestion;

    public UsedGoodsBoard dtoToUsedGoodsBoard(UsedGoodsBoardDto usedGoodsBoardDto){
        super.dtoToEntity(usedGoodsBoardDto);
        this.usedGoodsCategory = usedGoodsBoardDto.getUsedGoodsCategory();
        this.usedGoodsStatus = usedGoodsBoardDto.getUsedGoodsStatus();
        this.sellPrice = usedGoodsBoardDto.getSellPrice();
        this.priceSuggestion = usedGoodsBoardDto.isPriceSuggestion();
        return this;
    }
}
