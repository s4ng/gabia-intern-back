package com.gmarket.api.domain.board;

import com.gmarket.api.domain.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED) // JPA 상속 매핑 전략 (조인 전략)
@DiscriminatorColumn(name = "board_type") // 매핑 테이블 구분하는 컬럼
public class Board {
    @Id
    @GeneratedValue
    private long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id")
    private User writeId;

    private String title;

    private int viewCount;

    private String description;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    enum Status {
        CREATE,DELETE
    }
}
