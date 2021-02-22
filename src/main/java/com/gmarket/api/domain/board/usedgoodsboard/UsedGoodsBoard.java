package com.gmarket.api.domain.board.usedgoodsboard;

import com.gmarket.api.domain.board.Board;
import lombok.Getter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("USED")
@Getter
@PrimaryKeyJoinColumn(name = "used_board_id")
public class UsedGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    private GoodsCategory goodsCategory;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    private int sellPrice;

    enum GoodsCategory {
        DIGITAL, TICKET
    }

    enum GoodsStatus {
        NEW, ALMOST, USED
    }
}
