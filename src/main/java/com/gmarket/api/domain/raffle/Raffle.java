package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.raffle.enums.RaffleStatus;
import com.gmarket.api.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
public class Raffle {
    @Id @GeneratedValue
    private Long raffleId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private RaffleStatus status;


    public void delete() {
        this.status = RaffleStatus.DELETED;
    }

    public void reInsert() {
        this.status = RaffleStatus.CREATED;
    }
}
