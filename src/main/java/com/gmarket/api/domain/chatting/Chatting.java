package com.gmarket.api.domain.chatting;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Chatting {

    @Id @GeneratedValue
    private long chattingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "used_board_id")
    private Board usedBoardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyerId;

    private LocalDateTime createAt;
}
