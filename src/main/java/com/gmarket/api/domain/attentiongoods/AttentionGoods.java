package com.gmarket.api.domain.attentiongoods;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class AttentionGoods {
    @Id @GeneratedValue
    private long attentionGoodsId;

    @ManyToOne
    @JoinColumn(name = "register_id")
    private User registerId;

    @ManyToOne
    @JoinColumn(name = "used_board_id")
    private Board usedBoardId;

    private LocalDateTime createAt;
}
