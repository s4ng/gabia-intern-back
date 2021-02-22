package com.gmarket.api.domain.comment;

import com.gmarket.api.domain.board.Board;
import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
@DiscriminatorColumn(name = "comment_type") // 매핑 테이블 구분하는 컬럼
@Getter
public class Comment {
    @Id @GeneratedValue
    private long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "register_id")
    private User registerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board boardId;

    private String comment;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    enum Status {
        CREATE,DELETE
    }
}
