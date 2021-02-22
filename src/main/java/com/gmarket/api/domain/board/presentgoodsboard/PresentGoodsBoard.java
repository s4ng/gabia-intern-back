package com.gmarket.api.domain.board.presentgoodsboard;

import com.gmarket.api.domain.board.Board;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PRESENT")
@Getter
@PrimaryKeyJoinColumn(name = "present_board_id")
public class PresentGoodsBoard extends Board {

    @Enumerated(EnumType.STRING)
    private GoodsCategory goodsCategory;

    @Enumerated(EnumType.STRING)
    private GoodsStatus goodsStatus;

    private LocalDateTime raffleCloseAt;

    enum GoodsCategory {
        DIGITAL, TICKET
    }

    enum GoodsStatus {
        NEW, ALMOST, USED
    }
}
