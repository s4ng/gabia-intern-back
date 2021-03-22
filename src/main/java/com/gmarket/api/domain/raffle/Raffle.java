package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
public class Raffle {
    @Id @GeneratedValue
    private Long raffleId;

    @ManyToOne
    @JoinColumn(name = "present_board")
    private Board presentBoard;

    @ManyToOne
    @JoinColumn(name = "participant")
    private User participant;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CREATE, DELETE
    }

    public void delete() {
        this.status = Status.DELETE;
    }

    public void reInsert() {
        this.status = Status.CREATE;
    }
}
