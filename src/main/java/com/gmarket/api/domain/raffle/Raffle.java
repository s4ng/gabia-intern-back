package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Raffle {
    @Id @GeneratedValue
    private long raffleId;

    @ManyToOne
    @JoinColumn(name = "present_board_Id")
    private Board presentBoardId;

    @ManyToOne
    @JoinColumn(name = "participate_id")
    private User participateId;
}
